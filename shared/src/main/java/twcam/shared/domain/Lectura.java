package twcam.shared.domain;

import java.time.Instant;

public class Lectura {
    private int estacionId;
    
    private Instant timeStamp;

    private float nitricOxides;

    private float nitrogenDioxides;

    private float VOCs_NMHC;

    private float PM2_5;


    public int getEstacionId() {
        return estacionId;
    }

    public void setEstacionId(int estacionId) {
        this.estacionId = estacionId;
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

    public void setVOCs_NMHC(float vOCs_NMHC) {
        VOCs_NMHC = vOCs_NMHC;
    }

    public float getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(float pM2_5) {
        PM2_5 = pM2_5;
    }

}
