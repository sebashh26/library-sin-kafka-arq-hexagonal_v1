#### CREACION DEL JAR ####
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app
COPY ./pom.xml .
COPY ./lombok.config ./
RUN mvn -e -B dependency:go-offline
COPY ./src ./src
RUN mvn -e -B -Dmaven.test.skip=true package

#### FASE FINAL DE LA IMAGEN ####
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /workspace

ARG mypassword
ENV host="mysql_server"
ENV port="3306"
ENV database="librarybd"
ENV username="root"
ENV password=$mypassword

COPY --from=builder /app/target/mito-library*.jar app.jar

#no abre ningun puerto, es solo informativo
EXPOSE 8080 
ENTRYPOINT exec java -jar /workspace/app.jar