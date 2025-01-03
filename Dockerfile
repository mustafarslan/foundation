FROM openjdk:21-jdk-slim

ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:./core/src/main/resources/db/data/demo
ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=sa
ENV SPRING_H2_CONSOLE_ENABLED=true
ENV SPRING_H2_CONSOLE_PATH=/h2-console 
ENV JWT_SECRET=testSecretKeytestSecretKeytestSecretKeytestSecretKey
ENV JWT_EXPIRATION=86400000

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