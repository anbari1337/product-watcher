FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/productWatcher.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]