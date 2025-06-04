package twcam.proyecto.bicicletasdata.controllers;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.bicicletasdata.model.Evento;
import twcam.proyecto.bicicletasdata.repository.EventoRepository;
import twcam.proyecto.shared.EstadoDTO;

@RestController
@RequestMapping("/evento")
public class EventoController {

    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @PostMapping
    public Evento save(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @GetMapping("/by-parking/{id}")
    public Evento findParkingStatus(@PathVariable String id) {
        // Usa findFirstByParkingIdOrderByTimestampDesc
        return eventoRepository.findFirstByParkingIdOrderByTimestampDesc(id).orElse(null);
    }

    @GetMapping("/by-parking/{id}/interval")
    public List<Evento> findByFechas(@PathVariable String id,
            @RequestParam String from,
            @RequestParam String to) {
        LocalDateTime fromDate = LocalDateTime.parse(from);
        LocalDateTime toDate = LocalDateTime.parse(to);
        return eventoRepository.findByParkingIdAndTimestampBetween(id, fromDate, toDate);
    }

    @GetMapping("/top10")
    public List<EstadoDTO> top10ConMasBicisAhora() {
        LocalDateTime fechaActual = LocalDateTime.now();
        List<Evento> eventos = eventoRepository.findByTimestampLessThanEqual(fechaActual);

        // Agrupa por parkingId y coge el último de cada uno
        Map<String, Evento> ultimosEventos = new HashMap<>();
        for (Evento evento : eventos) {
            String parkingId = evento.getId();
            Evento actual = ultimosEventos.get(parkingId);
            if (actual == null || evento.getTimestamp().isAfter(actual.getTimestamp())) {
                ultimosEventos.put(parkingId, evento);
            }
        }

        // Ordena por bikesAvailable descendente y coge los 10 primeros
        return ultimosEventos.values().stream()
                .sorted(Comparator.comparingInt(Evento::getBikesAvailable).reversed())
                .limit(10)
                .map(e -> new EstadoDTO(e.getId(), e.getBikesAvailable(), e.getFreeParkingSpots()))
                .toList();
    }
}
