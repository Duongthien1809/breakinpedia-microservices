FROM openjdk:17
# Kopiere die JAR-Datei deines Spring Boot-Projekts in das Arbeitsverzeichnis
COPY ./target/order-1.0-SNAPSHOT.jar app.jar

# Definiere den Befehl, um deine Spring Boot-Anwendung auszuführen, wenn der Container gestartet wird
CMD ["java", "-jar", "app.jar"]