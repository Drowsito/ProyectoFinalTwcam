package twcam.shared.domain;

public class Parking {
    private String idparking;
    private String direction;
    private Integer bikesCapacity;
    private float latitude;
    private float longitude;

    public String getIdparking() {
        return idparking;
    }

    public void setIdparking(String idparking) {
        this.idparking = idparking;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getBikesCapacity() {
        return bikesCapacity;
    }

    public void setBikesCapacity(int bikesCapacity) {
        this.bikesCapacity = bikesCapacity;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
