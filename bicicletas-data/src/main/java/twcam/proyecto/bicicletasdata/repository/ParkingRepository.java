package twcam.proyecto.bicicletasdata.repository;

import twcam.proyecto.bicicletasdata.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {
}