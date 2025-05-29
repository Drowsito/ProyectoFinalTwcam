package twcam.proyecto.bicicletasdata.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "eventos")
public class Evento {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String mongoId;

    private String parkingId;

    private String operation;

    private Integer bikesAvailable;

    private Integer freeParkingSpots;

    private LocalDateTime timestamp;

    

    public Evento(String mongoId, String parkingId, String operation, Integer bikesAvailable, Integer freeParkingSpots,
            LocalDateTime timestamp) {
        this.mongoId = mongoId;
        this.parkingId = parkingId;
        this.operation = operation;
        this.bikesAvailable = bikesAvailable;
        this.freeParkingSpots = freeParkingSpots;
        this.timestamp = timestamp;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public String getId() {
        return parkingId;
    }

    public void setId(String id) {
        this.parkingId = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getBikesAvailable() {
        return bikesAvailable;
    }

    public void setBikesAvailable(Integer bikesAvailable) {
        this.bikesAvailable = bikesAvailable;
    }

    public Integer getFreeParkingSpots() {
        return freeParkingSpots;
    }

    public void setFreeParkingSpots(Integer freeParkingSpots) {
        this.freeParkingSpots = freeParkingSpots;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
