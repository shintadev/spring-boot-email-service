FROM openjdk:17-jdk-alpine

WORKDIR /app

ARG APP_VERSION

COPY target/spring-boot-email-service-${APP_VERSION}.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]