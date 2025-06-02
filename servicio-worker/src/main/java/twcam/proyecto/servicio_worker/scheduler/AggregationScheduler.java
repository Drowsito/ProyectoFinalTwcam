package twcam.proyecto.servicio_worker.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AggregationScheduler {

    private final RestTemplate restTemplate;

    @Value("${aggregation.task.token}")
    private String token;

    @Value("${aggregation.task.url}")
    private String url;

    public AggregationScheduler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRateString = "${aggregation.task.interval}")
    public void ejecutarTareaPeriodica() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            System.out.println("Llamada exitosa: " + response.getStatusCode());

        } catch (Exception e) {
            System.err.println("Error al llamar al endpoint protegido: " + e.getMessage());
        }
    }
}
