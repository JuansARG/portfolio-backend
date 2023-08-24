FROM gradle:7.6.1-jdk17-alpine
COPY . .
RUN gardle build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]

