FROM eclipse-temurin:21.0.6_7-jdk-jammy
EXPOSE 8080

WORKDIR /app
COPY target/ricardo.jar .

CMD java -jar ricardo.jar