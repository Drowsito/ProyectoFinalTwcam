package twcam.proyecto.ayuntamientodata.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirQuality {
    @JsonProperty("nitricOxides")
    private float nitricOxides;

    @JsonProperty("nitrogenDioxides")
    private float nitrogenDioxides;

    @JsonProperty("VOCs_NMHC")
    private float vocs_nmhc;

    @JsonProperty("PM2_5")
    private float pm2_5;

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
