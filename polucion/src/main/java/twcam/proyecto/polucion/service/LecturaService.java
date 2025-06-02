package twcam.proyecto.polucion.service;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import twcam.proyecto.poluciondata.model.mongo.Lectura;
import twcam.proyecto.poluciondata.repository.EstacionRepository;
import twcam.proyecto.poluciondata.repository.LecturaRepository;

@Service
public class LecturaService {
    private final LecturaRepository lecturaRepository;

    private final EstacionRepository estacionRepository;

    public LecturaService(LecturaRepository lecturaRepository, EstacionRepository estacionRepository) {
        this.lecturaRepository = lecturaRepository;
        this.estacionRepository = estacionRepository;
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
        if (!estacionRepository.existsById(id)) {
            throw new NoSuchElementException("No existe ninguna estación con ID " + id);
        }

        if (lectura.getTimeStamp() == null) {
            throw new IllegalArgumentException("Falta el campo 'timeStamp'");
        }

        lectura.setId(Integer.parseInt(id));

        return lecturaRepository.save(lectura);
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
        Instant fromInstant, toInstant;
        try {
            fromInstant = Instant.parse(from);
            toInstant = Instant.parse(to);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Formato de fecha incorrecto. Ejemplo correcto: 2024-03-01T14:30:57Z");
        }

        List<Lectura> lecturas = lecturaRepository.findByIdAndTimeStampBetweenOrderByTimeStampDesc(id, fromInstant,
                toInstant);

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
        List<Lectura> lecturas = lecturaRepository.findByIdOrderByTimeStampDesc(id);

        if (lecturas.isEmpty()) {
            throw new NoSuchElementException("No hay lecturas para la estación con id " + id);
        }

        return lecturas.get(0);
    }
}
