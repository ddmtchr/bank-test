package com.ddmtchr.banktest.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();


    @Before("execution(* com.ddmtchr.banktest.controller.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        try {
            String arguments = objectMapper.writeValueAsString(joinPoint.getArgs());
            logger.info("Method called: {}.{} with arguments: {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    arguments);
        } catch (JsonProcessingException e) {
            logger.error("Failed to log method arguments", e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.ddmtchr.banktest.controller.*.*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        try {
            String resultString = objectMapper.writeValueAsString(result);
            logger.info("Method returned: {}.{} with result: {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    resultString);
        } catch (JsonProcessingException e) {
            logger.error("Failed to log method result", e);
        }
    }
}
