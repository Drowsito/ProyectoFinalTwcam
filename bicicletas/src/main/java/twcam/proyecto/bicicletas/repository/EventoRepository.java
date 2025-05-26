package twcam.proyecto.bicicletas.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import twcam.proyecto.bicicletas.model.Evento;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {
    List<Evento> findByIdAndTimestamp(String id, LocalDateTime from, LocalDateTime to);
    
    Optional<Evento> findFirstByParkingIdOrderByTimestampDesc(String parkingId);

    List<Evento> findByTimestampLessThanEqual(LocalDateTime fecha);
}
