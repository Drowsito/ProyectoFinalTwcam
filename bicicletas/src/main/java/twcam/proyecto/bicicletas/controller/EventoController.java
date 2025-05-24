package twcam.proyecto.bicicletas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.bicicletas.model.Evento;
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
    public Evento crearEvento(@PathVariable String id, @RequestBody Evento evento) {
        evento.setId(id);
        evento.setTimestamp(LocalDateTime.now());
        return service.save(evento);
    }
}
