package twcam.proyecto.ayuntamiento.model.mongo;

public class EstacionAggregatedData {
    private int id;

    private float average_bikesAvailable;

    private AirQuality air_quality;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAverage_bikesAvailable() {
        return average_bikesAvailable;
    }

    public void setAverage_bikesAvailable(float average_bikesAvailable) {
        this.average_bikesAvailable = average_bikesAvailable;
    }

    public AirQuality getAir_quality() {
        return air_quality;
    }

    public void setAir_quality(AirQuality air_quality) {
        this.air_quality = air_quality;
    }

}
