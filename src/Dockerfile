FROM gradle:7.6.1-jdk17-alpine AS build
COPY . .
RUN gradle clean build -x test

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /build/libs/backend-0.0.1-SNAPSHOT.jar backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]

