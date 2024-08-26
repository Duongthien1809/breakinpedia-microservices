# Verwende ein offizielles OpenJDK-Base-Image als Basisimage
FROM openjdk:17
ARG JAR_FILE=target/*.jar
# Kopiere die JAR-Datei deines Spring Boot-Projekts in das Arbeitsverzeichnis
COPY ./target/currency-1.0-SNAPSHOT.jar app.jar

# Definiere den Befehl, um deine Spring Boot-Anwendung auszuführen, wenn der Container gestartet wird
CMD ["java", "-jar", "app.jar"]
