package twcam.proyecto.ayuntamiento.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import twcam.proyecto.ayuntamiento.model.mongo.AggregatedData;

public interface AggregatedDataRepository extends MongoRepository<AggregatedData, String> {
}