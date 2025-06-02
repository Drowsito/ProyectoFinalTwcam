USE polucion;

CREATE TABLE IF NOT EXISTS estacion (
  id VARCHAR(255) PRIMARY KEY,
  direccion VARCHAR(255) NOT NULL,
  latitud FLOAT NOT NULL,
  longitud FLOAT NOT NULL
);

DELETE FROM estacion;

INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('1', 'Avinguda de l''Universitat, 46100 Burjassot, Valencia', 39.512561, -0.424610);
INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('2', 'Carrer del Catedrátic José Beltrán Martinez, 2, 46980 Paterna, Valencia', 39.513506, -0.425217);
INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('3', 'Plaça de l''Ajuntament, 1, 46002 València, Valencia', 39.46975, -0.37639);

-- TODO: QUITAR ESTO:
-- MONGODB (todo hecho en GitBash):

-- Ejecutar Mongo en docker (solo se hace una vez):
-- docker run -d --name mongodb -p 27017:27017 -e MONGO_INITDB_DATABASE=polucion mongo:7.0

-- Ejecutar Mongo:
-- docker exec -it mongodb mongosh