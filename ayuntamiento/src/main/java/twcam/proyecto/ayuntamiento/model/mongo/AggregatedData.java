package twcam.proyecto.ayuntamiento.model.mongo;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "aggregated_data")
public class AggregatedData {
    @Id
    private String id;

    private Instant timeStamp;

    private List<EstacionAggregatedData> aggregatedData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<EstacionAggregatedData> getAggregatedData() {
        return aggregatedData;
    }

    public void setAggregatedData(List<EstacionAggregatedData> aggregatedData) {
        this.aggregatedData = aggregatedData;
    }
}
