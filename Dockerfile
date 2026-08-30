FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar blog-api.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "blog-api.jar"]
