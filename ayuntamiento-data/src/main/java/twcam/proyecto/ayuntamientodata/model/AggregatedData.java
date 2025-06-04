package twcam.proyecto.ayuntamientodata.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "aggregated_data")
public class AggregatedData {

    @JsonIgnore
    private String id;

    @JsonProperty("timeStamp")
    private Instant timeStamp;

    @JsonProperty("aggregatedData")
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
