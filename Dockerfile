FROM openjdk:17
WORKDIR /GestionRHEtPaie
COPY target/GestionRHEtPaie-0.0.1-SNAPSHOT.jar GestionRHEtPaie.jar
CMD ["java", "-jar", "GestionRHEtPaie.jar"]
EXPOSE 8082