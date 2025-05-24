package twcam.proyecto.bicicletas.repository;

import twcam.proyecto.bicicletas.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {
}