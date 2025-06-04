package twcam.proyecto.bicicletas.service.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import twcam.proyecto.shared.EstadoDTO;
import twcam.proyecto.shared.Evento;

@FeignClient(name = "eventodata", url = "${service.bicicletasdata.url}")
public interface EventoDataClient {

    @PostMapping("/evento")
    Evento save(@RequestBody Evento evento);

    @GetMapping("/evento/by-parking/{id}")
    Evento findParkingStatus(@PathVariable String id);

    @GetMapping("/evento/by-parking/{id}/interval")
    List<Evento> findByFechas(@PathVariable String id,
            @RequestParam String from,
            @RequestParam String to);

    @GetMapping("/evento/top10")
    List<EstadoDTO> top10ConMasBicisAhora();
}
