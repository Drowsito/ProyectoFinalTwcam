package twcam.proyecto.bicicletas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import twcam.proyecto.bicicletasdata.model.Evento;
import twcam.proyecto.bicicletas.service.EventoService;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/evento")
public class EventoController {
    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add evento", description = "Añade un evento al sistema")
    public Evento crearEvento(@PathVariable String id, @RequestBody Evento evento) {
        evento.setId(id);
        evento.setTimestamp(LocalDateTime.now());

        Evento ultimo = service.findParkingStatus(id);
        System.out.println(ultimo);
        int disponibles = ultimo != null ? ultimo.getBikesAvailable() : 0;
        int huecos = ultimo != null ? ultimo.getFreeParkingSpots() : 0;

        switch (evento.getOperation().toLowerCase()) {
            case "aparcamiento" -> {
                disponibles += 1;
                huecos -= 1;
            }
            case "alquiler" -> {
                disponibles -= 1;
                huecos += 1;
            }
            case "reposición_múltiple" -> {
                disponibles += 2;
                huecos -= 2;
            }
            case "retirada_múltiple" -> {
                disponibles -= 2;
                huecos += 2;
            }
            default -> throw new IllegalArgumentException("Operación no válida: " + evento.getOperation());
        }

        evento.setBikesAvailable(disponibles);
        evento.setFreeParkingSpots(huecos);

        return service.save(evento);
    }

}
