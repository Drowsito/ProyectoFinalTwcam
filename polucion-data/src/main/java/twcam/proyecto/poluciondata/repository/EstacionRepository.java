package twcam.proyecto.poluciondata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import twcam.proyecto.poluciondata.model.Estacion;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, String> {

}
