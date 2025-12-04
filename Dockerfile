# Stage 1: Build the app with Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the app using a smaller JDK image
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/social_music-0.0.1-SNAPSHOT.jar app.jar

# Set port
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
