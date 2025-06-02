package main.java.twcam.proyecto.estacion_worker.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twcam.proyecto.shared.Lectura;

import java.time.Instant;
import java.util.Random;

@Component
public class LecturaScheduler {

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    @Value("${lectura.task.token}")
    private String token;

    @Value("${lectura.task.url}")
    private String baseUrl;

    public LecturaScheduler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRateString = "${lectura.task.interval}")
    public void ejecutarTareaDeLectura() {
        try {
            int estacionId = random.nextInt(3) + 1;

            Lectura lectura = new Lectura();
            lectura.setTimeStamp(Instant.now());
            lectura.setNitricOxides(randomFloat(10, 100));
            lectura.setNitrogenDioxides(randomFloat(20, 200));
            lectura.setVOCs_NMHC(randomFloat(0.5f, 5.0f));
            lectura.setPM2_5(randomFloat(5, 80));

            String url = baseUrl + "/" + estacionId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Lectura> request = new HttpEntity<>(lectura, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            System.out.println("Lectura enviada a estación " + estacionId + " (Status: " + response.getStatusCode() + ")");

        } catch (Exception e) {
            System.err.println("Error al enviar lectura: " + e.getMessage());
        }
    }

    private float randomFloat(float min, float max) {
        return min + random.nextFloat() * (max - min);
    }
}
