package twcam.proyecto.bicicletas.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import twcam.proyecto.bicicletas.model.EstadoDTO;
import twcam.proyecto.bicicletas.model.Evento;
import twcam.proyecto.bicicletas.model.Parking;
import twcam.proyecto.bicicletas.service.EventoService;
import twcam.proyecto.bicicletas.service.ParkingService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ParkingController {
    private final ParkingService parkingService;
    private final EventoService eventoService;

    public ParkingController(ParkingService parkingService, EventoService eventoService) {
        this.parkingService = parkingService;
        this.eventoService = eventoService;
    }

    @GetMapping("/aparcamientos")
    @Operation(summary = "Get parkings", description = "Muestra todos los aparcamientos")
    public ResponseEntity<?> listAll() {
        List<Parking> parkings = parkingService.findAll();
        if (parkings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay aparcamientos disponibles");
        }
        return ResponseEntity.ok(parkings);
    }

    @PostMapping("/aparcamiento")
    @Operation(summary = "Add parking", description = "Añade un nuevo aparcamiento")
    public ResponseEntity<?> add(@RequestBody Parking parking) {
        Parking saved = parkingService.save(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aparcamiento creado con ID: " + saved.getIdparking());
    }

    @PutMapping("/aparcamiento/{id}")
    @Operation(summary = "Update parking", description = "Modifica un parking")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Parking parking) {
        boolean exists = parkingService.existsById(id);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró un aparcamiento con ID: " + id);
        }
        parking.setIdparking(id);
        Parking updated = parkingService.save(parking);
        return ResponseEntity.ok("Aparcamiento actualizado con ID: " + updated.getIdparking());
    }

    @DeleteMapping("/aparcamiento/{id}")
    @Operation(summary = "Delete parking", description = "Elimina un parking pasado un id")
    public ResponseEntity<?> delete(@PathVariable String id) {
        boolean exists = parkingService.existsById(id);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe aparcamiento con ID: " + id);
        }
        parkingService.delete(id);
        return ResponseEntity.ok("Aparcamiento con ID " + id + " eliminado correctamente.");
    }

    @GetMapping("/aparcamiento/{id}/status")
    @Operation(summary = "Get the status of a parking", description = "Muestra el estado de un parking pasado el id")
    public ResponseEntity<?> statusParking(@PathVariable String id) {
        if (!parkingService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el aparcamiento con ID: " + id);
        }

        Evento evento = eventoService.findParkingStatus(id);
        if (evento == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay datos de estado para el aparcamiento con ID: " + id);
        }

        EstadoDTO estado = new EstadoDTO(evento.getId(), evento.getBikesAvailable(), evento.getFreeParkingSpots());
        return ResponseEntity.ok(estado);
    }

    @GetMapping(value = "/aparcamiento/{id}/status", params = { "from", "to" })
    @Operation(summary = "Get events by dates", description = "Muestra los cambios de estado de una parada en un cierto espacio de tiempo")
    public ResponseEntity<?> eventsBetweenDates(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        if (!parkingService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el aparcamiento con ID: " + id);
        }

        if (from.isAfter(to)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La fecha inicial debe ser anterior a la final.");
        }

        List<Evento> eventos = eventoService.findByFechas(id, from, to);
        if (eventos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay eventos registrados entre las fechas proporcionadas.");
        }

        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/aparcamiento/available")
    @Operation(summary = "Top 10 parkings with free bikes", description = "Consulta los 10 parkings con más bicis disponibles en este momento")
    public ResponseEntity<?> top10Ahora() {
        List<EstadoDTO> lista = eventoService.top10ConMasBicisAhora();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No hay datos disponibles en este momento.");
        }

        return ResponseEntity.ok(lista);
    }

}
