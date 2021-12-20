# Build stage
FROM maven:3.8-jdk-11-slim AS build

COPY ./ /app
WORKDIR /app
RUN mvn -f pom.xml clean package

# Package stage
FROM openjdk:11-jre-slim

COPY --from=build /app/target/java-clean-scaffold-0.0.1.jar /app/bin/app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/bin/app.jar"]