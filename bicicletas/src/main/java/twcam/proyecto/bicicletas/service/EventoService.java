package twcam.proyecto.bicicletas.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import twcam.proyecto.bicicletas.service.clientes.EventoDataClient;
import twcam.proyecto.shared.EstadoDTO;
import twcam.proyecto.shared.Evento;

@Service
public class EventoService {

    private final EventoDataClient eventoDataClient;

    public EventoService(EventoDataClient eventoDataClient) {
        this.eventoDataClient = eventoDataClient;
    }

    public Evento save(Evento evento) {
        return eventoDataClient.save(evento);
    }

    public Evento findParkingStatus(String id) {
        return eventoDataClient.findParkingStatus(id);
    }

    public List<Evento> findByFechas(String id, LocalDateTime from, LocalDateTime to) {
        // Convertimos las fechas a String para pasarlas como query params
        String fromStr = from.toString();
        String toStr = to.toString();

        return eventoDataClient.findByFechas(id, fromStr, toStr);
    }

    public List<EstadoDTO> top10ConMasBicisAhora() {
        return eventoDataClient.top10ConMasBicisAhora();
    }

}
