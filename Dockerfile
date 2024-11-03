FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files from core directory
COPY core/gradlew.bat ./
COPY core/settings.gradle core/build.gradle ./
COPY core/gradle ./gradle/
COPY core/gradlew ./

# Copy source code from core directory
COPY core/src ./src

# Copy the bash script to run gradlew
COPY run_gradlew.sh ./

# Make gradlew and bash script executable
RUN chmod +x ./gradlew
RUN chmod +x ./run_gradlew.sh

# Run the bash script to build the application without running tests
RUN ./run_gradlew.sh

# Copy the built JAR file to the container
COPY core/build/libs/*.jar app.jar

# Expose port 8081 for the application
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]