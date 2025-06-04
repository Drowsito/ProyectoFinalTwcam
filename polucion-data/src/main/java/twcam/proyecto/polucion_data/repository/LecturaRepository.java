package twcam.proyecto.polucion_data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import twcam.proyecto.polucion_data.model.Lectura;

import java.time.Instant;
import java.util.List;

public interface LecturaRepository extends MongoRepository<Lectura, String> {
    List<Lectura> findByIdOrderByTimeStampDesc(int id);

    List<Lectura> findByIdAndTimeStampBetweenOrderByTimeStampDesc(int id, Instant desde, Instant hasta);

}
