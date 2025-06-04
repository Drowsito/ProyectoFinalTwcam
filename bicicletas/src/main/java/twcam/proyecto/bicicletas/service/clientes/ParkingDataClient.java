package twcam.proyecto.bicicletas.service.clientes;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import twcam.proyecto.shared.Parking;

@FeignClient(name = "parkingdata", url = "${service.bicicletasdata.url}")
public interface ParkingDataClient {

    @GetMapping("/parking")
    List<Parking> findAll();

    @GetMapping("/parking/{id}")
    Optional<Parking> findById(@PathVariable String id);

    @GetMapping("/parking/exists/{id}")
    boolean existsById(@PathVariable String id);

    @PostMapping("/parking")
    Parking save(@RequestBody Parking parking);

    @DeleteMapping("/parking/{id}")
    void delete(@PathVariable String id);
}
