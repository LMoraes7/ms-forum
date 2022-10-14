FROM openjdk:17-alpine

WORKDIR /app
COPY ./target/ms-forum-0.0.1-SNAPSHOT.jar ./forum.jar

ENV SERVER_PORT 8080
ENV MYSQL_HOST localhost
ENV MYSQL_PORT 3306
ENV MYSQL_DATABASE forum
ENV MYSQL_USER root
ENV MYSQL_PASSWORD root

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "forum.jar"]