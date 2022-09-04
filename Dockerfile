FROM openjdk:17-alpine
ARG JAR_FILE
COPY target/Exam-0.0.1-SNAPSHOT.jar /spring-boot-example-project-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/Exam-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]