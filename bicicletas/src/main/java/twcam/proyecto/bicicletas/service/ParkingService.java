package twcam.proyecto.bicicletas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import twcam.proyecto.bicicletas.service.clientes.ParkingDataClient;
import twcam.proyecto.shared.Parking;

@Service
public class ParkingService {

    private final ParkingDataClient parkingDataClient;

    public ParkingService(ParkingDataClient parkingDataClient) {
        this.parkingDataClient = parkingDataClient;
    }

    public List<Parking> findAll() {
        return parkingDataClient.findAll();
    }

    public Optional<Parking> findById(String id) {
        return parkingDataClient.findById(id);
    }

    public boolean existsById(String id) {
        return parkingDataClient.existsById(id);
    }

    public Parking save(Parking aparcamiento) {
        return parkingDataClient.save(aparcamiento);
    }

    public void delete(String id) {
        parkingDataClient.delete(id);
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
        if (latitude == null) {
            return "El campo 'latitude' no puede ser nulo";
        }
        if (longitude == null) {
            return "El campo 'longitude' no puede ser nulo.";
        }
        return null;
    }

}
