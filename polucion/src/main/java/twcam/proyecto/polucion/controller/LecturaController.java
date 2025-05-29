package twcam.proyecto.polucion.controller;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import twcam.proyecto.poluciondata.model.mongo.Lectura;
import twcam.proyecto.poluciondata.repository.EstacionRepository;
import twcam.proyecto.poluciondata.repository.LecturaRepository;

@RestController
public class LecturaController {

    private final LecturaRepository lecturaRepository;

    private final EstacionRepository estacionRepository;

    public LecturaController(LecturaRepository lecturaRepository, EstacionRepository estacionRepository) {
        this.lecturaRepository = lecturaRepository;

        this.estacionRepository = estacionRepository;
    }

    // TODO: Añadir seguridad: rol "estacion" (al OpenAPI también?)
    @PostMapping("/estacion/{id}")
    @Operation(summary = "Registra una nueva lectura de sensores", description = "Guarda una lectura de calidad del aire enviada por una estación de medición")
    @ApiResponse(responseCode = "201", description = "Lectura registrada correctamente")
    @ApiResponse(responseCode = "400", description = "Petición mal escrita")
    @ApiResponse(responseCode = "404", description = "La estación con ese id no existe")
    public ResponseEntity<?> registrarLectura(@PathVariable String id, @RequestBody Lectura lectura) {
        if (!estacionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe ninguna estación con ID " + id);
        }

        if (lectura.getTimeStamp() == null) {
            return ResponseEntity.badRequest().body("Falta el campo 'timeStamp'");
        }

        lectura.setId(Integer.parseInt(id));
        Lectura nueva = lecturaRepository.save(lectura);

        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @GetMapping("/estacion/{id}/status")
    @Operation(summary = "Obtener la última lectura o las lecturas por intervalo", description = "Devuelve la última lectura de una estación o todas las lecturas en un intervalo si se pasan parámetros 'from' y 'to'")
    @ApiResponse(responseCode = "200", description = "Lecturas devueltas correctamente")
    @ApiResponse(responseCode = "400", description = "Fechas mal escritas")
    @ApiResponse(responseCode = "404", description = "No se encontraron lecturas")
    public ResponseEntity<?> obtenerLecturas(
            @PathVariable int id,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {

        // Si hay parámetros en el request se busca por intervalo
        if (from != null && to != null) {
            Instant fromInstant, toInstant;

            try {
                fromInstant = Instant.parse(from);
                toInstant = Instant.parse(to);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest()
                        .body("Formato de fecha incorrecto. Ejemplo correcto: 2024-03-01T14:30:57Z");
            }

            List<Lectura> lecturas = lecturaRepository.findByIdAndTimeStampBetweenOrderByTimeStampDesc(id,
                    fromInstant, toInstant);

            if (lecturas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No hay lecturas para la estación " + id + " en ese intervalo");
            }

            return ResponseEntity.ok(lecturas);
        }

        // Si no hay parámetros en el request se devuelve la última lectura
        List<Lectura> lecturas = lecturaRepository.findByIdOrderByTimeStampDesc(id);

        if (lecturas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay lecturas para la estación con id " + id);
        }

        return ResponseEntity.ok(lecturas.get(0));
    }

}
