# Utilise l’image officielle de Java pour builder
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Utilise une image plus légère pour exécuter
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "app.jar"]
