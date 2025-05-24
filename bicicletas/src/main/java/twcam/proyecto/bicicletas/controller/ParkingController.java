package twcam.proyecto.bicicletas.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import twcam.proyecto.bicicletas.model.Evento;
import twcam.proyecto.bicicletas.model.Parking;
import twcam.proyecto.bicicletas.service.EventoService;
import twcam.proyecto.bicicletas.service.ParkingService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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
    @Operation(summary="Get parkings", description="Muestra todos los aparcamientos")
    public List<Parking> listAll() {
        return parkingService.findAll();
    }

    @PostMapping("/aparcamiento")
    @Operation(summary="Add parking", description="Añade un nuevo aparcamiento")
    public Parking add(@RequestBody Parking parking) {
        return parkingService.save(parking);
    }

    @PutMapping("/aparcamiento/{id}")
    @Operation(summary="Update parking", description="Modifica un parking")
    public Parking update(@PathVariable String id, @RequestBody Parking parking) {
        parking.setIdparking(id);
        return parkingService.save(parking);
    }

    @DeleteMapping("/aparcamiento/{id}")
    @Operation(summary="Delete parking", description="Elimina un parking pasado un id")
    public void delete(@PathVariable String id) {
        parkingService.delete(id);
    }

    @GetMapping("/aparcamiento/{id}/status")
    @Operation(summary="Get events in parking", description="Muestra el estado en el que se encuentra una parada pasado el id")
    public List<Evento> eventsParking(@PathVariable String id) {
        return eventoService.findById(id);
    }

    @GetMapping(value = "/aparcamiento/{id}/status", params = { "from", "to" })
    @Operation(summary="Get events by dates", description="Muestra los cambios de estado de una parada en un cierto espacio de tiempo")
    public List<Evento> eventsBetweenDates(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return eventoService.findByFechas(id, from, to);
    }
}
