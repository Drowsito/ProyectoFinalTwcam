package twcam.proyecto.ayuntamientodata.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import twcam.proyecto.ayuntamientodata.model.mongo.AggregatedData;

public interface AggregatedDataRepository extends MongoRepository<AggregatedData, String> {
    @Query(sort = "{ timeStamp : -1 }")
    AggregatedData findFirstByOrderByTimeStampDesc();
}