package twcam.proyecto.ayuntamiento.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import twcam.proyecto.shared.Estacion;
import twcam.proyecto.shared.Evento;
import twcam.proyecto.shared.Lectura;
import twcam.proyecto.shared.Parking;
import twcam.proyecto.ayuntamientodata.model.mongo.AggregatedData;
import twcam.proyecto.ayuntamientodata.model.mongo.AirQuality;
import twcam.proyecto.ayuntamientodata.model.mongo.EstacionAggregatedData;
import twcam.proyecto.ayuntamientodata.repository.AggregatedDataRepository;

@Service
public class AggregatedDataService {

    private final RestTemplate restTemplate;
    private final AggregatedDataRepository repository;

    public AggregatedDataService(RestTemplate restTemplate, AggregatedDataRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    // TODO: Repasar este método y mirarlo bien. Básicamente generado con Chati
    public AggregatedData generarDatos() {
        String baseUrlBicis = "http://localhost:8081";
        String baseUrlPolucion = "http://localhost:8082";
        String baseUrlAyuntamiento = "http://localhost:8083";

        // 1. Obtener lista de aparcamientos
        Parking[] aparcamientos = restTemplate.getForObject(baseUrlBicis + "/aparcamiento", Parking[].class);
        if (aparcamientos == null || aparcamientos.length == 0)
            return null;

        List<EstacionAggregatedData> resultado = new ArrayList<>();

        for (Parking parking : aparcamientos) {
            String parkingId = parking.getIdparking();

            // 2. Calcular media de bicis disponibles para ese aparcamiento
            Evento[] registrosBicis = restTemplate.getForObject(
                    baseUrlBicis + "/aparcamiento/" + parkingId + "/status?from=2000-01-01T00:00:00Z&to="
                            + Instant.now().toString(),
                    Evento[].class);

            float mediaBicis = 0;
            if (registrosBicis != null && registrosBicis.length > 0) {
                int suma = 0;
                for (Evento r : registrosBicis)
                    suma += r.getBikesAvailable();
                mediaBicis = (float) suma / registrosBicis.length;
            }

            // 3. Obtener la estación más cercana usando el endpoint de ayuntamiento
            Estacion estacionCercana = restTemplate.getForObject(
                    baseUrlAyuntamiento + "/estacionCercana?lat=" + parking.getLatitude()
                            + "&lon=" + parking.getLongitude(),
                    Estacion.class);

            if (estacionCercana == null)
                continue; // Saltamos si no se encuentra estación cercana

            // 4. Calcular media de contaminantes para esa estación
            Lectura[] lecturas = restTemplate.getForObject(
                    baseUrlPolucion + "/estacion/" + estacionCercana.getId() + "/status?from=2000-01-01T00:00:00Z&to="
                            + Instant.now().toString(),
                    Lectura[].class);

            float nOx = 0, nDiox = 0, vocs = 0, pm = 0;
            if (lecturas != null && lecturas.length > 0) {
                for (Lectura l : lecturas) {
                    nOx += l.getNitricOxides();
                    nDiox += l.getNitrogenDioxides();
                    vocs += l.getVOCs_NMHC();
                    pm += l.getPM2_5();
                }
                int n = lecturas.length;
                nOx /= n;
                nDiox /= n;
                vocs /= n;
                pm /= n;
            }

            AirQuality calidad = new AirQuality();
            calidad.setNitricOxides(nOx);
            calidad.setNitrogenDioxides(nDiox);
            calidad.setVocs_nmhc(vocs);
            calidad.setPm2_5(pm);

            // 5. Montar el agregado para este aparcamiento
            EstacionAggregatedData e = new EstacionAggregatedData();
            e.setId(Integer.parseInt(parkingId));
            e.setAverage_bikesAvailable(mediaBicis);
            e.setAir_quality(calidad);

            resultado.add(e);
        }

        // 6. Crear el documento y guardarlo en MongoDB
        AggregatedData doc = new AggregatedData();
        doc.setTimeStamp(Instant.now());
        doc.setAggregatedData(resultado);

        return repository.save(doc);
    }

    public AggregatedData obtenerUltimoRegistro() {
        return repository.findFirstByOrderByTimeStampDesc();
    }
}