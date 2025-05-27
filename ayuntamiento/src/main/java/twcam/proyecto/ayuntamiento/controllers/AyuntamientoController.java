package twcam.proyecto.ayuntamiento.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import twcam.shared.domain.Estacion;

@RestController
public class AyuntamientoController {

    private final RestTemplate restTemplate;

    public AyuntamientoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/estacion")
    @Operation(summary = "Crea una estación", description = "Crea una estación redirigiendo la petición al microservicio 'polucion'")
    @ApiResponse(responseCode = "201", description = "Estación creada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "409", description = "Ya existe una estación con el id indicado")
    public ResponseEntity<?> reenviarEstacion(@RequestBody Estacion estacion) {
        String urlPolucion = "http://localhost:8082/estacion";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(urlPolucion, estacion, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con polucion-service");
        }
    }
}
