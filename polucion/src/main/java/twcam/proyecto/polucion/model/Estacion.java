package twcam.proyecto.polucion.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estacion")
public class Estacion {
    @Id
    private String id;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private float latitud;

    @Column(nullable = false)
    private float longitud;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
}