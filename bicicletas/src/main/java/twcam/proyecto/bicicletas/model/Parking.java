package twcam.proyecto.bicicletas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idparking;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private Integer bikesCapacity;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;

    public Integer getIdparking() {
        return idparking;
    }

    public void setIdparking(Integer idparking) {
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
