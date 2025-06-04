package twcam.proyecto.polucion_data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import twcam.proyecto.polucion_data.model.Estacion;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, String> {

}
