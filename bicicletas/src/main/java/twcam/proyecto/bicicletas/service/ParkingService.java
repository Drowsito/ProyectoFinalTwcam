package twcam.proyecto.bicicletas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import twcam.proyecto.bicicletas.model.Parking;
import twcam.proyecto.bicicletas.repository.ParkingRepository;

@Service
public class ParkingService {
    private final ParkingRepository repo;

    public ParkingService(ParkingRepository repo){
        this.repo = repo;
    }

    public List<Parking> findAll() {
        return repo.findAll();
    }

    public Optional<Parking> findById(String id) {
        return repo.findById(id);
    }

    public Parking save(Parking aparcamiento) {
        return repo.save(aparcamiento);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }
}
