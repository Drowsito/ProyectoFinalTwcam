package twcam.proyecto.bicicletasdata.imports;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import twcam.proyecto.bicicletasdata.model.Evento;
import twcam.proyecto.bicicletasdata.repository.EventoRepository;

@Service
public class ImportServiceMongo {

    @Autowired
    private EventoRepository eventoRepository;

    public void importarEventos(Resource resource) {
        if (eventoRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try (Scanner scanner = new Scanner(resource.getFile())) {
                while (scanner.hasNextLine()) {
                    String json = scanner.nextLine();
                    Evento evento = mapper.readValue(json, Evento.class);
                    eventoRepository.save(evento);
                }
                System.out.println("Eventos insertados correctamente.");
            } catch (Exception e) {
                System.err.println("Error al importar eventos: " + e.getMessage());
            }
        }
    }
}
