FROM openjdk:8-jdk-alpine
MAINTAINER foxminded.com
COPY target/schedule-1.jar schedule.jar
WORKDIR ./
CMD ["java","-jar","schedule.jar"]