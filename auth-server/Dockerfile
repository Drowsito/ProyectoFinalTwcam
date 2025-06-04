FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/authorization-server-0.1.0-SNAPSHOT.jar app.jar

EXPOSE 9000

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java", "-jar", "app.jar"]
