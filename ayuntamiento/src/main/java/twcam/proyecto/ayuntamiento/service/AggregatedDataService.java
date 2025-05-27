package twcam.proyecto.ayuntamiento.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import twcam.proyecto.ayuntamiento.model.mongo.AggregatedData;
import twcam.proyecto.ayuntamiento.model.mongo.AirQuality;
import twcam.proyecto.ayuntamiento.model.mongo.EstacionAggregatedData;
import twcam.proyecto.ayuntamiento.repository.AggregatedDataRepository;
import twcam.shared.domain.Estacion;
import twcam.shared.domain.Lectura;

@Service
public class AggregatedDataService {

    private final RestTemplate restTemplate;
    private final AggregatedDataRepository repository;

    public AggregatedDataService(RestTemplate restTemplate, AggregatedDataRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    // TODO: Repasar este método y mirarlo bien. Básicamente generado con Chati
    //
    public AggregatedData generarDatos() {
        String baseUrlPolucion = "http://localhost:8082";

        // 1. Obtener lista de estaciones
        Estacion[] estaciones = restTemplate.getForObject(baseUrlPolucion + "/estaciones", Estacion[].class);
        if (estaciones == null || estaciones.length == 0)
            return null;

        List<EstacionAggregatedData> resultado = new ArrayList<>();

        for (Estacion estacion : estaciones) {
            int estacionId = Integer.parseInt(estacion.getId());

            try {
                // 2. Obtener la última lectura
                // TODO: Se tiene que obtener la media de bcicletas y 
                // de contaminantes? En qué intervalo de tiempo?
                Lectura lectura = restTemplate.getForObject(
                        baseUrlPolucion + "/estacion/" + estacionId + "/status",
                        Lectura.class);

                if (lectura == null)
                    continue;

                AirQuality calidad = new AirQuality(
                        lectura.getNitricOxides(),
                        lectura.getNitrogenDioxides(),
                        lectura.getVOCs_NMHC(),
                        lectura.getPM2_5());

                EstacionAggregatedData e = new EstacionAggregatedData();
                e.setId(estacionId);
                e.setAverage_bikesAvailable(0); // TODO: Parte de Pablo
                e.setAir_quality(calidad);

                resultado.add(e);
            } catch (HttpClientErrorException.NotFound e) {
                // Si no hay lecturas, simplemente se ignora la estación
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // 3. Crear y guardar el documento en MongoDB
        AggregatedData doc = new AggregatedData();
        doc.setTimeStamp(Instant.now());
        doc.setAggregatedData(resultado);

        return repository.save(doc);
    }
}