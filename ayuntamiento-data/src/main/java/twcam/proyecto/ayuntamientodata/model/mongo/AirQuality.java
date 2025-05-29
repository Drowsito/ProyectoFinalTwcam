package twcam.proyecto.ayuntamientodata.model.mongo;

public class AirQuality {
    public AirQuality(float nitricOxides, float nitrogenDioxides, float vOCs_NMHC, float pM2_5) {
        this.nitricOxides = nitricOxides;
        this.nitrogenDioxides = nitrogenDioxides;
        VOCs_NMHC = vOCs_NMHC;
        PM2_5 = pM2_5;
    }

    private float nitricOxides;

    private float nitrogenDioxides;

    private float VOCs_NMHC;

    private float PM2_5;

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
