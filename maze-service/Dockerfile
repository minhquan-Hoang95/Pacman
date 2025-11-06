# ---- Stage 1: build the app ----
FROM gradle:8.10-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

# ---- Stage 2: run the app ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# Render automatically provides $PORT
ENV SERVER_PORT=${PORT}
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
