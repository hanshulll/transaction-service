FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY target/ledger-consumer-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
