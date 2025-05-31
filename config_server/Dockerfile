# Usamos una imagen base con Java
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR construido a la imagen
COPY target/config_server-0.0.1-SNAPSHOT.jar config-server.jar

# Puerto en el que corre la app
EXPOSE 8888

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "config-server.jar"]
