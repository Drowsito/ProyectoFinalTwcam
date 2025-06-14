package twcam.proyecto.shared;

import java.time.LocalDateTime;

public class Evento {
    private String mongoId;
    private String parkingId;
    private String operation;
    private Integer bikesAvailable;
    private Integer freeParkingSpots;
    private LocalDateTime timestamp;

    public Evento() {
    }

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
