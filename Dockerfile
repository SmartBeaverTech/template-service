# ===== BUILD STAGE =====
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy only pom.xml first (for dependency caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build the fat jar
COPY src ./src
RUN mvn clean package spring-boot:repackage -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the fat jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar"]
