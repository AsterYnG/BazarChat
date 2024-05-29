FROM openjdk:22-jdk-oracle

COPY target/*.jar bazar.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "bazar.jar"]
