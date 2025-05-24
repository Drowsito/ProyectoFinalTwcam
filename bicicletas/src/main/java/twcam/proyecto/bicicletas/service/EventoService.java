package twcam.proyecto.bicicletas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import twcam.proyecto.bicicletas.model.Evento;
import twcam.proyecto.bicicletas.repository.EventoRepository;

@Service
public class EventoService {
    private final EventoRepository repo;

    public EventoService(EventoRepository repo) {
        this.repo = repo;
    }

    public Evento save(Evento evento) {
        return repo.save(evento);
    }

    public List<Evento> findById(String id){
        return repo.findById(id);
    } 

    public List<Evento> findByFechas(String id, LocalDateTime from, LocalDateTime to) {
        return repo.findByIdAndTimestamp(id, from, to);
    }
}
