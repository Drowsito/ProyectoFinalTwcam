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

    public Optional<Parking> findById(Integer id) {
        return repo.findById(id);
    }

    public Parking save(Parking aparcamiento) {
        return repo.save(aparcamiento);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
