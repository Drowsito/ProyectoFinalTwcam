package twcam.proyecto.ayuntamientodata.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import twcam.proyecto.ayuntamientodata.model.mongo.AggregatedData;

public interface AggregatedDataRepository extends MongoRepository<AggregatedData, String> {
}