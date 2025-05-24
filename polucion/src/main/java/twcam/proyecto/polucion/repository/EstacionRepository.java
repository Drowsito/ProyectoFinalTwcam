package twcam.proyecto.polucion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import twcam.proyecto.polucion.model.Estacion;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, String> {

}
