package twcam.proyecto.poluciondata.imports;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import twcam.proyecto.poluciondata.model.mongo.Lectura;
import twcam.proyecto.poluciondata.repository.LecturaRepository;

@Service
public class ImportServiceMongo {

    @Autowired
    private LecturaRepository lecturaRepository;

    public void importarLecturas(Resource resource) {
        // Si no hay datos en la base de datos de Mongo, se insertan
        if (lecturaRepository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try (Scanner scanner = new Scanner(resource.getFile())) {
                while (scanner.hasNextLine()) {
                    String json = scanner.nextLine();
                    Lectura lectura = mapper.readValue(json, Lectura.class);
                    lecturaRepository.save(lectura);
                }
                System.out.println("Lecturas insertadas correctamente.");
            } catch (Exception e) {
                System.err.println("Error al importar lecturas: " + e.getMessage());
            }
        }
    }
}
