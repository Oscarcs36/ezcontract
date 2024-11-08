# Usa una imagen base con OpenJDK
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR de tu aplicación al contenedor
    COPY target/ezcontract-0.0.1-SNAPSHOT.jar /app/ezcontract-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que tu API va a escuchar (por defecto, Spring Boot usa el puerto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "my-api.jar"]
