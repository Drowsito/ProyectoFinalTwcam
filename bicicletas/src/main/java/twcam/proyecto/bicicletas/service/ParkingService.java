package twcam.proyecto.bicicletas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import twcam.proyecto.bicicletas.model.Parking;
import twcam.proyecto.bicicletas.repository.ParkingRepository;

@Service
public class ParkingService {
    private final ParkingRepository repo;

    public ParkingService(ParkingRepository repo) {
        this.repo = repo;
    }

    public List<Parking> findAll() {
        return repo.findAll();
    }

    public Optional<Parking> findById(String id) {
        return repo.findById(id);
    }

    public boolean existsById(String id) {
        return repo.existsById(id);
    }

    public Parking save(Parking aparcamiento) {
        return repo.save(aparcamiento);
    }

    public void delete(String id) {
        repo.deleteById(id);
    }

    public String validarCamposObligatorios(Parking parking) {
        Integer capacity = parking.getBikesCapacity();
        Float latitude = parking.getLatitude();
        Float longitude = parking.getLongitude();

        if (parking.getIdparking() == null || parking.getIdparking().isBlank()) {
            return "El campo 'idparking' es obligatorio.";
        }
        if (parking.getDirection() == null || parking.getDirection().isBlank()) {
            return "El campo 'direction' es obligatorio.";
        }
        if (capacity == null) {
            return "El campo 'bikesCapacity' es obligatorio";
        }
        if (latitude == null || latitude == 0.0f) {
            return "El campo 'latitude' no puede ser 0 ni nulo";
        }
        if (longitude == null ||longitude == 0.0f) {
            return "El campo 'longitude' no puede ser 0.";
        }
        return null;
    }

}
