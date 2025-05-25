-- En teoría esto no se está usando. Lo dejo para guardar todos los scripts que uso.

CREATE TABLE IF NOT EXISTS estacion (
    id VARCHAR(4) PRIMARY KEY,
    direccion VARCHAR(50) NOT NULL,
    latitud INT NOT NULL,
    longitud INT NOT NULL
);

DELETE FROM estacion;

INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('1', 'Avinguda de l''Universitat, 46100 Burjassot, Valencia', 39.512561, -0.424610);
INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('2', 'Carrer del Catedrátic José Beltrán Martinez, 2, 46980 Paterna, Valencia', 39.513506, -0.425217);



-- MONGODB (todo hecho en GitBash):

-- Ejecutar Mongo en docker (solo se hace una vez):
-- docker run -d --name mongodb -p 27017:27017 -e MONGO_INITDB_DATABASE=polucion mongo:7.0

-- Ejecutar Mongo:
-- docker exec -it mongodb mongosh
