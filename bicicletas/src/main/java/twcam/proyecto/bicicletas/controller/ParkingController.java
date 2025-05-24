package twcam.proyecto.bicicletas.controller;

import org.springframework.web.bind.annotation.RestController;

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
    public List<Parking> listAll() {
        return parkingService.findAll();
    }

    @PostMapping("/aparcamiento")
    public Parking add(@RequestBody Parking parking) {
        return parkingService.save(parking);
    }

    @PutMapping("/aparcamiento/{id}")
    public Parking update(@PathVariable String id, @RequestBody Parking parking) {
        parking.setIdparking(id);
        return parkingService.save(parking);
    }

    @DeleteMapping("/aparcamiento/{id}")
    public void delete(@PathVariable String id) {
        parkingService.delete(id);
    }

    @GetMapping("/aparcamiento/{id}/status")
    public List<Evento> eventsParking(@PathVariable String id) {
        return eventoService.findById(id);
    }

    @GetMapping(value = "/aparcamiento/{id}/status", params = { "from", "to" })
    public List<Evento> eventsBetweenDates(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return eventoService.findByFechas(id, from, to);
    }
}
