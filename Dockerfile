# Use a lightweight JDK image
FROM openjdk:17-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/jwt-auth-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]