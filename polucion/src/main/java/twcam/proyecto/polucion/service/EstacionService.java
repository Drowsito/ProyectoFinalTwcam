package twcam.proyecto.polucion.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import twcam.proyecto.polucion.service.clientes.EstacionDataClient;
import twcam.proyecto.shared.Estacion;

@Service
public class EstacionService {

    private final EstacionDataClient estacionDataClient;

    public EstacionService(EstacionDataClient estacionDataClient) {
        this.estacionDataClient = estacionDataClient;
    }

    /**
     * Crea una nueva estación
     * 
     * @param estacion La estación a crear
     * @return La estación creada
     * @throws IllegalArgumentException si faltan campos obligatorios
     * @throws IllegalStateException    si ya existe una estación con ese id
     */
    public Estacion crearEstacion(Estacion estacion) {
        if (estacion.getId() == null || estacion.getId().isBlank()) {
            throw new IllegalArgumentException("Falta introducir el campo 'id'");
        }
        if (estacion.getDireccion() == null || estacion.getDireccion().isBlank()) {
            throw new IllegalArgumentException("Falta introducir el campo 'direccion'");
        }
        if (estacionDataClient.existsById(estacion.getId())) {
            throw new IllegalStateException("Ya existe una estación con id " + estacion.getId());
        }
        return estacionDataClient.create(estacion);
    }

    /**
     * Elimina una estación
     * 
     * @param id Identificador de la estación a borrar
     */
    public void eliminarEstacion(String id) {
        if (!estacionDataClient.existsById(id)) {
            throw new NoSuchElementException("No existe ninguna estación con id " + id);
        }
        estacionDataClient.delete(id);
    }

    /**
     * Actualiza una estación
     * 
     * @param id       Identificador de la estación a actualizar
     * @param estacion Estación con datos actualizados
     * 
     * @return La estación actualizada
     */
    public Estacion actualizarEstacion(String id, Estacion estacion) {
        if (!estacionDataClient.existsById(id)) {
            throw new NoSuchElementException("No existe ninguna estación con id " + id);
        }
        if (estacion.getDireccion() == null || estacion.getDireccion().isBlank()) {
            throw new IllegalArgumentException("Falta introducir el campo 'direccion'");
        }
        // Reutilizas la lógica de crear un nuevo objeto con los datos correctos
        return estacionDataClient.update(id, estacion);
    }

    /**
     * Obtiene todas las estaciones de la base de datos
     * 
     * @return Lista con todas las estaciones
     */
    public List<Estacion> obtenerTodas() {
        return estacionDataClient.getAll();
    }
}
