# Stage 1: Build the Spring Boot application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the parent POM from the root (it’s available because the build context is ".")
COPY pom.xml /app/parent-pom.xml

# Copy the child module's pom.xml and src folder
COPY history-service/pom.xml .
COPY history-service/src ./src

# Install the parent POM before building the module
RUN mvn -f /app/parent-pom.xml install -N -DskipTests

RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight JDK image
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/history-service-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]