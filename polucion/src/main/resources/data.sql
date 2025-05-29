-- En teoría esto no se está usando. Lo dejo para guardar todos los scripts que uso.
DELETE FROM estacion;

INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('1', 'Avinguda de l''Universitat, 46100 Burjassot, Valencia', 39.512561, -0.424610);
INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('2', 'Carrer del Catedrátic José Beltrán Martinez, 2, 46980 Paterna, Valencia', 39.513506, -0.425217);
INSERT INTO estacion (id, direccion, latitud, longitud) VALUES ('3', 'Plaça de l''Ajuntament, 1, 46002 València, Valencia', 39.46975, -0.37639);

-- MONGODB (todo hecho en GitBash):

-- Ejecutar Mongo en docker (solo se hace una vez):
-- Polución:
-- docker run -d --name mongodb -p 27017:27017 -e MONGO_INITDB_DATABASE=polucion mongo:7.0
-- Ayuntamiento:
-- docker run -d --name mongodbayunt -p 27018:27018 -e MONGO_INITDB_DATABASE=ayuntamiento mongo:7.0

-- Ejecutar Mongo:
-- docker exec -it mongodb mongosh
-- docker exec -it mongodbayunt mongosh
