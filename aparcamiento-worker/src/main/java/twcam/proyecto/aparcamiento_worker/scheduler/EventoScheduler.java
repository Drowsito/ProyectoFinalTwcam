package twcam.proyecto.aparcamiento_worker.scheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twcam.proyecto.shared.OperacionDTO;

import java.util.List;
import java.util.Random;

@Component
public class EventoScheduler {

    private final RestTemplate restTemplate;
    private final Random random = new Random();

    @Value("${evento.task.token}")
    private String token;

    @Value("${evento.task.url}")
    private String baseUrl;

    public EventoScheduler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRateString = "${evento.task.interval}")
    public void ejecutarTareaDeEvento() {
        try {
            int parkingId = random.nextInt(3) + 1;

            List<String> operaciones = List.of("aparcamiento", "alquiler", "reposición_múltiple", "retirada_múltiple");
            String operacionElegida = operaciones.get(random.nextInt(operaciones.size()));

            OperacionDTO operacionDTO = new OperacionDTO(operacionElegida);

            String url = baseUrl + "/" + parkingId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<OperacionDTO> request = new HttpEntity<>(operacionDTO, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class);

            System.out.println("Evento enviado al parking " + parkingId + " con operación '" + operacionElegida
                    + "'. Respuesta: " + response.getStatusCode());

        } catch (Exception e) {
            System.err.println("Error al enviar evento: " + e.getMessage());
        }
    }
}