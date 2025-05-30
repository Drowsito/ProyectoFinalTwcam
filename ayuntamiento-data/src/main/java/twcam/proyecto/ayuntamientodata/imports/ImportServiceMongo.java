package twcam.proyecto.ayuntamientodata.imports;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import twcam.proyecto.ayuntamientodata.model.mongo.AggregatedData;
import twcam.proyecto.ayuntamientodata.repository.AggregatedDataRepository;

@Service
public class ImportServiceMongo {

    @Autowired
    private AggregatedDataRepository repository;

    public void importarDatos(Resource resource) {
        if (repository.count() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            try {
                AggregatedData aggregatedData = mapper.readValue(resource.getFile(), AggregatedData.class);
                repository.save(aggregatedData);
                System.out.println("Datos agregados insertados correctamente.");
            } catch (IOException e) {
                System.err.println("Error al importar datos: " + e.getMessage());
            }
        }
    }
}