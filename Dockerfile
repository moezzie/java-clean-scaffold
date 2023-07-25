# Build stage
FROM maven:3.8-jdk-11-slim AS builder

COPY ./ /app
WORKDIR /app
RUN mvn -f pom.xml clean package

# Package stage
FROM openjdk:11-jre-slim

COPY --from=builder /app/target/java-clean-scaffold-0.0.1.jar /app/bin/app.jar
COPY ./src/main/resources/application* /app/bin/

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/bin/app.jar","--spring.config.name=application", "--spring.config.location=file:///app/resources/", "--spring.profiles.active=${APP_ACTIVE_PROFILE:-default}"]