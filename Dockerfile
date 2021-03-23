FROM openjdk:11
ARG JAR_FILE=build/libs/*all.jar
COPY ${JAR_FILE} key-manager-rest-0.1-runner.jar
ENTRYPOINT ["java","-jar","/key-manager-rest-0.1-runner.jar"]