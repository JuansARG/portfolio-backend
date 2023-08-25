FROM gradle:7.6.1-jdk17-alpine AS build
COPY . .
RUN gradle build -x test
ENTRYPOINT ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]

