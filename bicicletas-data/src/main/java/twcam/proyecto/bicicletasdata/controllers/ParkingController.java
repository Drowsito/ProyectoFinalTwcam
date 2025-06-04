package twcam.proyecto.bicicletasdata.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.bicicletasdata.model.Parking;
import twcam.proyecto.bicicletasdata.repository.ParkingRepository;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingRepository parkingRepository;

    public ParkingController(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @GetMapping
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Parking> findById(@PathVariable String id) {
        return parkingRepository.findById(id);
    }

    @GetMapping("/exists/{id}")
    public boolean existsById(@PathVariable String id) {
        return parkingRepository.existsById(id);
    }

    @PostMapping
    public Parking save(@RequestBody Parking parking) {
        return parkingRepository.save(parking);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        parkingRepository.deleteById(id);
    }
}
