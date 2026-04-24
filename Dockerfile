FROM openjdk:22-jdk
ADD target/finance-project.jar finance-project.jar
ENTRYPOINT ["java", "-jar", "/finance-project.jar"]