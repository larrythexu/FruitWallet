# Build
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY FruitWalletBackend/pom.xml .
COPY FruitWalletBackend/src src
RUN ./mvnw clean package -DskipTests

# Run
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar fruitwalletbackend.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fruitwalletbackend.jar"]
