FROM openjdk:8-jdk-alpine
MAINTAINER foxminded.com
COPY target/schedule-1.jar schedule.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/schedule.jar"]