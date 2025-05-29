package twcam.proyecto.poluciondata.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import twcam.proyecto.poluciondata.model.mongo.Lectura;

import java.time.Instant;
import java.util.List;

public interface LecturaRepository extends MongoRepository<Lectura, String> {
    List<Lectura> findByIdOrderByTimeStampDesc(int estacionId);

    List<Lectura> findByIdAndTimeStampBetweenOrderByTimeStampDesc(int estacionId, Instant desde, Instant hasta);

}
