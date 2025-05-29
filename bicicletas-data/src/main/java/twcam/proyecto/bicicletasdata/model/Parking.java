package twcam.proyecto.bicicletasdata.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking")
public class Parking {
    @Id
    private String idparking;

    @Column(nullable = false)
    private String direction;

    @Column(name = "bikes_capacity", nullable = false)
    private Integer bikesCapacity;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
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
