FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY apiEncryptionKey.jks apiEncryptionKey.jks
COPY build/libs/catalog-service-1.0.0.jar CatallogServer.jar
ENTRYPOINT ["java", "-jar", "CatallogServer.jar"]