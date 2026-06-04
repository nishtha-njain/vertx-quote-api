# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn clean package

# Run stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/quote-api-1.0.0.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]