package twcam.proyecto.ayuntamiento.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // TODO: Añadir seguridad: rol "admin" (al OpenAPI también?)
    @PostMapping("/estacion")
    @Operation(summary = "Crea una estación", description = "Crea una estación redirigiendo la petición al microservicio 'polucion'")
    @ApiResponse(responseCode = "201", description = "Estación creada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "409", description = "Ya existe una estación con el id indicado")
    public ResponseEntity<?> crearEstacion(@RequestBody Estacion estacion) {
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

    // TODO: Añadir seguridad: rol "admin" (al OpenAPI también?)
    @DeleteMapping("/estacion/{id}")
    @Operation(summary = "Elimina una estación", description = "Elimina una estación redirigiendo la petición al microservicio 'polucion'")
    @ApiResponse(responseCode = "200", description = "Estación eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró la estación con ese id")
    public ResponseEntity<?> eliminarEstacion(@PathVariable String id) {
        String urlPolucion = "http://localhost:8082/estacion/" + id;

        try {
            restTemplate.delete(urlPolucion);

            return ResponseEntity.ok("Estación con id " + id + " eliminada correctamente");
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la estación con id " + id);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con polucion-service");
        }
    }

    // TODO: Añadir seguridad: rol "admin" (al OpenAPI también?)
    @PutMapping("/estacion/{id}")
    @Operation(summary = "Modifica una estación", description = "Modifica una estación redirigiendo la petición al microservicio 'polucion'")
    @ApiResponse(responseCode = "200", description = "Estación actualizada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios en la petición")
    @ApiResponse(responseCode = "404", description = "No existe una estación con el id indicado")
    public ResponseEntity<?> modificarEstacion(@PathVariable String id, @RequestBody Estacion estacion) {
        String urlPolucion = "http://localhost:8082/estacion/" + estacion.getId();

        try {
            HttpEntity<Estacion> request = new HttpEntity<>(estacion);
            ResponseEntity<String> response = restTemplate.exchange(urlPolucion, HttpMethod.PUT, request, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con polucion-service");
        }
    }

}
