FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /build/target/stockmanagement-0.0.1-SNAPSHOT.jar app.jar

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

CMD ["java", "-jar", "app.jar"]
