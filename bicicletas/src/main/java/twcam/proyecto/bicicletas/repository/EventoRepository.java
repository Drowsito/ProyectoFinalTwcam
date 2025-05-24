package twcam.proyecto.bicicletas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import twcam.proyecto.bicicletas.model.Evento;

@Repository
public interface EventoRepository extends MongoRepository<Evento, Integer> {
    List<Evento> findByIdAndTimestamp(String id, LocalDateTime from, LocalDateTime to);
    
    List<Evento> findById(String id);
}
