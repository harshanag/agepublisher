
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY target/agepublisher-0.0.1-SNAPSHOT.jar /app/agepublisher.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app/agepublisher.jar"]