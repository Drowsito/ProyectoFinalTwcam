package twcam.proyecto.poluciondata.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@Document(collection = "lecturas")
public class Lectura {
    @Id
    @JsonIgnore
    private String mongoId;

    @JsonProperty("id")
    private int id;

    @JsonProperty("timeStamp")
    private Instant timeStamp;

    @JsonProperty("nitricOxides")
    private float nitricOxides;

    @JsonProperty("nitrogenDioxides")
    private float nitrogenDioxides;

    @JsonProperty("VOCs_NMHC")
    private float VOCs_NMHC;

    @JsonProperty("PM2_5")
    private float PM2_5;

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getNitricOxides() {
        return nitricOxides;
    }

    public void setNitricOxides(float nitricOxides) {
        this.nitricOxides = nitricOxides;
    }

    public float getNitrogenDioxides() {
        return nitrogenDioxides;
    }

    public void setNitrogenDioxides(float nitrogenDioxides) {
        this.nitrogenDioxides = nitrogenDioxides;
    }

    public float getVOCs_NMHC() {
        return VOCs_NMHC;
    }

    public void setVOCs_NMHC(float VOCs_NMHC) {
        this.VOCs_NMHC = VOCs_NMHC;
    }

    public float getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(float PM2_5) {
        this.PM2_5 = PM2_5;
    }
}
