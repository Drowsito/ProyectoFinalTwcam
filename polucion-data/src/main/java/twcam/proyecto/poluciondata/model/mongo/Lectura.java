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
    private float vocs_nmhc;

    @JsonProperty("PM2_5")
    private float pm2_5;

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

    public float getVocs_nmhc() {
        return vocs_nmhc;
    }

    public void setVocs_nmhc(float vocs_nmhc) {
        this.vocs_nmhc = vocs_nmhc;
    }

    public float getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(float pm2_5) {
        this.pm2_5 = pm2_5;
    }

}
