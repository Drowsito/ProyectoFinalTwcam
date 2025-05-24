package twcam.proyecto.bicicletas.controller;

import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.bicicletas.model.Parking;
import twcam.proyecto.bicicletas.service.ParkingService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ParkingController {
    private final ParkingService service;

    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @GetMapping("/aparcamientos")
    public List<Parking> listAll() {
        return service.findAll();
    }
    
    @PostMapping("/aparcamiento")
    public Parking add(@RequestBody Parking parking) {
        return service.save(parking);
    }

    @PutMapping("/aparcamiento/{id}")
    public Parking update(@PathVariable String id, @RequestBody Parking parking) {
        parking.setIdparking(id);
        return service.save(parking);
    }

    @DeleteMapping("/aparcamiento/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}   
