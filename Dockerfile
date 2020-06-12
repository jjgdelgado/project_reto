#JAR
FROM openjdk:8
WORKDIR /
ADD target/project-reto.jar project-reto.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "project-reto.jar"]