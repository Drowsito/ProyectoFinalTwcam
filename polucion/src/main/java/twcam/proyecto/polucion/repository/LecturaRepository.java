package twcam.proyecto.polucion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import twcam.proyecto.polucion.model.mongo.Lectura;

import java.time.Instant;
import java.util.List;

public interface LecturaRepository extends MongoRepository<Lectura, String> {
    List<Lectura> findByEstacionIdOrderByTimeStampDesc(int estacionId);

    List<Lectura> findByEstacionIdAndTimeStampBetweenOrderByTimeStampDesc(int estacionId, Instant desde, Instant hasta);

}
