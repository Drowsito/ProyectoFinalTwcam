package twcam.proyecto.polucion.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import twcam.proyecto.poluciondata.model.Estacion;
import twcam.proyecto.poluciondata.repository.EstacionRepository;

@Service
public class EstacionService {
    private final EstacionRepository estacionRepository;

    public EstacionService(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
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
        if (estacionRepository.existsById(estacion.getId())) {
            throw new IllegalStateException("Ya existe una estación con id " + estacion.getId());
        }

        return estacionRepository.save(estacion);
    }

    /**
     * Elimina una estación
     * 
     * @param id Identificador de la estación a borrar
     */
    public void eliminarEstacion(String id) {
        if (!estacionRepository.existsById(id)) {
            throw new NoSuchElementException("No existe ninguna estación con id " + id);
        }

        estacionRepository.deleteById(id);
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
        if (!estacionRepository.existsById(id)) {
            throw new NoSuchElementException("No existe ninguna estación con id " + id);
        }

        if (estacion.getDireccion() == null || estacion.getDireccion().isBlank()) {
            throw new IllegalArgumentException("Falta introducir el campo 'direccion'");
        }

        Estacion estacionBD = estacionRepository.findById(id).get();
        estacionBD.setDireccion(estacion.getDireccion());
        estacionBD.setLatitud(estacion.getLatitud());
        estacionBD.setLongitud(estacion.getLongitud());

        return estacionRepository.save(estacionBD);
    }

    /**
     * Obtiene todas las estaciones de la base de datos
     * 
     * @return Lista con todas las estaciones
     */
    public List<Estacion> obtenerTodas() {
        return estacionRepository.findAll();
    }
}
