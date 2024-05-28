FROM maven:3.9.5-eclipse-temurin-17 as build

WORKDIR /usr/src/app
COPY pom.xml .
COPY src ./src
ENV DATABASE_URL=jdbc:postgresql://postgres:5432/bank-test
RUN mvn clean package
ENTRYPOINT ["java","-jar","target/bank-test-0.0.1-SNAPSHOT.jar"]