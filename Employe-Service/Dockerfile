FROM openjdk:17
EXPOSE 8082
COPY target/backendmicro-0.0.1-SNAPSHOT.jar employe-service.jar
ENTRYPOINT ["java", "-jar", "/employe-service.jar"]