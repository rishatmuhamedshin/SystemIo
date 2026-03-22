FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar

RUN mkdir ./logs
EXPOSE 8337
ENTRYPOINT ["java", "-jar", "app.jar"]