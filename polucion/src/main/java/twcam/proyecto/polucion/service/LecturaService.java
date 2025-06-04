package twcam.proyecto.polucion.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import twcam.proyecto.polucion.service.clientes.EstacionDataClient;
import twcam.proyecto.polucion.service.clientes.LecturaDataClient;
import twcam.proyecto.shared.Lectura;

@Service
public class LecturaService {
    private final EstacionDataClient estacionDataClient;

    private final LecturaDataClient lecturaDataClient;

    public LecturaService(EstacionDataClient estacionDataClient, LecturaDataClient lecturaDataClient) {
        this.estacionDataClient = estacionDataClient;
        this.lecturaDataClient = lecturaDataClient;
    }

    /**
     * Registra una lectura en la base de datos
     * 
     * @param id      Identificador de la estación en la que se hace la lectura
     * @param lectura Lectura a registrar
     * 
     * @return Lectura guardada en la base de datos
     */
    public Lectura registrarLectura(String id, Lectura lectura) {
        if (!estacionDataClient.existsById(id)) {
            throw new NoSuchElementException("No existe ninguna estación con ID " + id);
        }
        if (lectura.getTimeStamp() == null) {
            throw new IllegalArgumentException("Falta el campo 'timeStamp'");
        }

        lectura.setId(Integer.parseInt(id));
        return lecturaDataClient.create(lectura);
    }

    /**
     * Obtiene todas las lecturas de una estación comprendidas en un intervalo
     * 
     * @param id   Identificador de la estación
     * @param from Fecha de inicio del intervalo
     * @param to   Fecha de fin del intervalo
     * 
     * @return Lista de lecturas en ese intervalo
     */
    public List<Lectura> obtenerLecturasEnIntervalo(int id, String from, String to) {
        List<Lectura> lecturas = lecturaDataClient.findByIdAndTimeStampBetweenOrderByTimeStampDesc(id, from, to);

        if (lecturas.isEmpty()) {
            throw new NoSuchElementException("No hay lecturas para la estación " + id + " en ese intervalo");
        }

        return lecturas;
    }

    /**
     * Obtiene la última lectura registrada en una estación
     * 
     * @param id Identificador de la estación
     * 
     * @return Última lectura registrada en la estación
     */
    public Lectura obtenerUltimaLectura(int id) {
        List<Lectura> lecturas = lecturaDataClient.findByIdOrderByTimeStampDesc(id);

        if (lecturas.isEmpty()) {
            throw new NoSuchElementException("No hay lecturas para la estación con id " + id);
        }

        return lecturas.get(0);
    }
}
