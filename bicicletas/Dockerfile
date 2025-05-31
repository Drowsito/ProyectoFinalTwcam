# Usa una imagen de Java optimizada para producción
FROM eclipse-temurin:17-jre-alpine

# Crea el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado (ajusta el nombre si es necesario)
COPY target/bicicletas-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que usa tu aplicación
EXPOSE 8081

# Define variables de entorno para configuración externa (usaremos ConfigMaps/Secrets)
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
