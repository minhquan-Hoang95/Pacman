# ---- Stage 1: Build ----
FROM gradle:8.10-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# ---- Stage 2: Run ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy only the executable JAR (not the plain one)
COPY --from=build /app/build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# Default port for local + Render
ENV PORT=8080
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
