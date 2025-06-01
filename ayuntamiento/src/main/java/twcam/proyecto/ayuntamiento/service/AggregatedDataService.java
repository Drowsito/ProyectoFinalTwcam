package twcam.proyecto.ayuntamiento.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import twcam.proyecto.shared.Estacion;
import twcam.proyecto.shared.EstadoDTO;
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

    public AggregatedData generarDatos() {
        List<EstacionAggregatedData> resultado = new ArrayList<>();
        String baseUrlBicis = "http://localhost:8081";
        String baseUrlPolucion = "http://localhost:8082";
        String baseUrlAyuntamiento = "http://localhost:8083";

        // Se obtiene la lista de aparcamientos
        Parking[] aparcamientos = restTemplate.getForObject(baseUrlBicis + "/aparcamientos", Parking[].class);
        if (aparcamientos == null || aparcamientos.length == 0)
            return null;

        // Se recorre cada uno de los aparcamientos
        for (Parking parking : aparcamientos) {
            // Se obtiene el id de este aparcamiento
            String parkingId = parking.getIdparking();

            // Se buscan los registros del aparcamiento entre el año 2000 y ahora mismo
            EstadoDTO[] registrosBicis = restTemplate.getForObject(baseUrlBicis + "/aparcamiento/" + parkingId
                    + "/status?from=2000-01-01T00:00:00Z&to=" + Instant.now().toString(), EstadoDTO[].class);

            // Se calcula la media de bicis disponibles para ese
            // aparcamiento recorriendo cada registro del aparcamiento
            float mediaBicis = 0;
            if (registrosBicis != null && registrosBicis.length > 0) {
                int suma = 0;

                for (EstadoDTO r : registrosBicis)
                    suma += r.bikesAvailable();

                mediaBicis = (float) suma / registrosBicis.length;
            }

            // Se obtiene la estación más cercana al aparcamiento
            Estacion estacionCercana = restTemplate.getForObject(baseUrlAyuntamiento + "/estacionCercana?lat="
                    + parking.getLatitude() + "&lon=" + parking.getLongitude(), Estacion.class);
            if (estacionCercana == null)
                continue;

            // Se obtienen las lecturas de esa estación entre el año 2000 y ahora mismo
            Lectura[] lecturas = restTemplate.getForObject(baseUrlPolucion + "/estacion/" + estacionCercana.getId()
                    + "/status?from=2000-01-01T00:00:00Z&to=" + Instant.now().toString(), Lectura[].class);

            // Se calcula la media de contaminantes para esa estación
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

            // Se monta el AirQuality para el documento Mongo
            AirQuality calidad = new AirQuality();
            calidad.setNitricOxides(nOx);
            calidad.setNitrogenDioxides(nDiox);
            calidad.setVocs_nmhc(vocs);
            calidad.setPm2_5(pm);

            // Se monta el EstacionAggregatedData para el documento Mongo
            EstacionAggregatedData e = new EstacionAggregatedData();
            e.setId(Integer.parseInt(parkingId));
            e.setAverage_bikesAvailable(mediaBicis);
            e.setAir_quality(calidad);

            // Se guarda el resultado
            resultado.add(e);
        }

        // Se crea el documento con la fecha actual y se guarda en MongoDB
        AggregatedData doc = new AggregatedData();
        doc.setTimeStamp(Instant.now());
        doc.setAggregatedData(resultado);

        return repository.save(doc);
    }

    public AggregatedData obtenerUltimoRegistro() {
        return repository.findFirstByOrderByTimeStampDesc();
    }
}