# Java 17 runtime
FROM eclipse-temurin:17-jre

# App folder
WORKDIR /app

# Copy the built jar
COPY target/*.jar app.jar

# App port
EXPOSE 8088

# Run
ENTRYPOINT ["java","-jar","/app/app.jar"]
