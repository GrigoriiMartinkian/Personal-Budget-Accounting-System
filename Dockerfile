FROM openjdk:22-jdk
COPY target/finance-project.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]