FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar blog-api.jar
COPY images/ /app/images/

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "blog-api.jar"]
