package twcam.proyecto.bicicletas.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import twcam.proyecto.shared.EstadoDTO;
import twcam.proyecto.bicicletasdata.model.Evento;
import twcam.proyecto.bicicletasdata.repository.EventoRepository;

@Service
public class EventoService {
    private final EventoRepository repo;

    public EventoService(EventoRepository repo) {
        this.repo = repo;
    }

    public Evento save(Evento evento) {
        return repo.save(evento);
    }

    public Evento findParkingStatus(String id) {
        return repo.findFirstByParkingIdOrderByTimestampDesc(id).orElse(null);
    }

    public List<Evento> findByFechas(String id, LocalDateTime from, LocalDateTime to) {
        return repo.findByIdAndTimestamp(id, from, to);
    }

    public List<EstadoDTO> top10ConMasBicisAhora() {
        LocalDateTime fecha = LocalDateTime.now();
        List<Evento> eventos = repo.findByTimestampLessThanEqual(fecha);

        Map<String, Evento> ultimosEventos = new HashMap<>();

        for (Evento evento : eventos) {
            String parkingId = evento.getId();
            Evento actual = ultimosEventos.get(parkingId);
            if (actual == null || evento.getTimestamp().isAfter(actual.getTimestamp())) {
                ultimosEventos.put(parkingId, evento);
            }
        }

        return ultimosEventos.values().stream()
                .sorted(Comparator.comparingInt(Evento::getBikesAvailable).reversed())
                .limit(10)
                .map(e -> new EstadoDTO(e.getId(), e.getBikesAvailable(), e.getFreeParkingSpots()))
                .toList();
    }

}
