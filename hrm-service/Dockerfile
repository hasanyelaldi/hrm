FROM openjdk:15-jdk-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/hrm-service-1.0-SNAPSHOT.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
