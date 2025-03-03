# Stage 1: Build the Spring Boot application using Maven
#FROM maven:3.8.5-openjdk-21 AS build

FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the parent POM from the root (it’s available because the build context is ".")
#COPY pom.xml /app/parent-pom.xml
#
## Copy the child module's pom.xml and src folder
#COPY customer-service/pom.xml .
#COPY customer-service/src ./src


COPY ./pom.xml /app/parent-pom.xml
COPY ./customer-service/pom.xml /app/pom.xml
COPY ./customer-service/src /app/src

# Install the parent POM before building the module
#RUN mvn -f /app/parent-pom.xml install -N -DskipTests

RUN mvn clean package -DskipTests

# Stage 2: Run the application using a lightweight JDK image
# the line below is for the compilation:
FROM eclipse-temurin:21-jre
WORKDIR /app

#This copies the JAR file built in the build stage (/app/target/demo-0.0.1-SNAPSHOT.jar) to the current /app directory of the final image and renames it to app.jar.
#RUN ls -al /app/
COPY --from=build /app/target/customer-service-0.0.1-SNAPSHOT.jar app.jar
#RUN ls -al /app/target/
ENTRYPOINT ["java", "-jar", "app.jar"]