package com.ddmtchr.banktest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankTestApplication.class, args);
    }

}
