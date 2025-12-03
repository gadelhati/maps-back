FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

COPY --from=build /target/maps.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]