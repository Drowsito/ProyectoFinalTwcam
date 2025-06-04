package twcam.proyecto.polucion.service.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import twcam.proyecto.shared.Lectura;

@FeignClient(name = "poluciondatalectura", url = "http://localhost:8087")
public interface LecturaDataClient {

    @PostMapping("/lectura")
    Lectura create(@RequestBody Lectura lectura);

    @GetMapping("/lectura/by-estacion/{id}")
    List<Lectura> findByIdOrderByTimeStampDesc(@PathVariable int id);

    @GetMapping("/lectura/by-estacion/{id}/range")
    List<Lectura> findByIdAndTimeStampBetweenOrderByTimeStampDesc(
            @PathVariable int id,
            @RequestParam String from,
            @RequestParam String to);
}
