FROM openjdk:17-ea-slim

EXPOSE 8080

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]