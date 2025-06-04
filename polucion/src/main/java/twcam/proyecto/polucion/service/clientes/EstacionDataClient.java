package twcam.proyecto.polucion.service.clientes;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import twcam.proyecto.shared.Estacion;

@FeignClient(name = "poluciondataestacion", url = "http://localhost:8087")
public interface EstacionDataClient {
    @GetMapping("/estacion")
    List<Estacion> getAll();

    @PostMapping("/estacion")
    Estacion create(@RequestBody Estacion estacion);

    @PutMapping("/estacion/{id}")
    Estacion update(@PathVariable String id, @RequestBody Estacion estacion);

    @DeleteMapping("/estacion/{id}")
    void delete(@PathVariable String id);

    @GetMapping("/estacion/{id}")
    Optional<Estacion> getById(@PathVariable String id);

    @GetMapping("/estacion/exists/{id}")
    boolean existsById(@PathVariable String id);
}
