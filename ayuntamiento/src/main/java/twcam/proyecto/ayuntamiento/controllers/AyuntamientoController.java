package twcam.proyecto.ayuntamiento.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import twcam.proyecto.shared.Estacion;
import twcam.proyecto.shared.EstadoDTO;
import twcam.proyecto.shared.Parking;

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

    @PostMapping("/aparcamiento")
    @Operation(summary = "Crea un aparcamiento", description = "Crea un aparcamiento redirigiendo la petición al microservicio 'bicicletas'")
    @ApiResponse(responseCode = "201", description = "Aparcamiento creado correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "409", description = "Ya existe un aparcamiento con el id indicado")
    public ResponseEntity<?> crearParking(@RequestBody Parking parking) {
        String urlBicicleta = "http://localhost:8081/aparcamiento";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(urlBicicleta, parking, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con bicicletas-service");
        }
    }

    // TODO: Añadir seguridad: rol "admin" (al OpenAPI también?)
    @DeleteMapping("/aparcamiento/{id}")
    @Operation(summary = "Elimina un parking", description = "Elimina un parking redirigiendo la petición al microservicio 'bicicletas'")
    @ApiResponse(responseCode = "200", description = "Parking eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró el parking con ese id")
    public ResponseEntity<?> eliminarParking(@PathVariable String id) {
        String urlBicicleta = "http://localhost:8081/aparcamiento/" + id;

        try {
            restTemplate.delete(urlBicicleta);

            return ResponseEntity.ok("Estación con id " + id + " eliminada correctamente");
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la estación con id " + id);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con bicicletas-service");
        }
    }

    // TODO: Añadir seguridad: rol "admin" (al OpenAPI también?)
    @PutMapping("/aparcamiento/{id}")
    @Operation(summary = "Modifica un parking", description = "Modifica una parking redirigiendo la petición al microservicio 'bicicletas'")
    @ApiResponse(responseCode = "200", description = "Parking actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios en la petición")
    @ApiResponse(responseCode = "404", description = "No existe una parking con el id indicado")
    public ResponseEntity<?> modificarParking(@PathVariable String id, @RequestBody Parking parking) {
        String urlBicicleta = "http://localhost:8081/aparcamiento/" + id;

        try {
            HttpEntity<Parking> request = new HttpEntity<>(parking);
            ResponseEntity<String> response = restTemplate.exchange(urlBicicleta, HttpMethod.PUT, request,
                    String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con bicicletas-service");
        }
    }

    @GetMapping("/aparcamientoCercano")
    @Operation(summary = "Aparcamiento más cercano con bicis disponibles", description = "Devuelve el más cercano a la posición indicada")
    @ApiResponse(responseCode = "200", description = "Se ha encontrado un aparcamiento disponible cercano")
    @ApiResponse(responseCode = "404", description = "No hay aparcamientos disponibles o con bicis cerca de la ubicación dada")
    @ApiResponse(responseCode = "500", description = "Error de comunicación con el servicio de bicicletas")
    public ResponseEntity<?> aparcamientoCercano(@RequestParam Float lat,
            @RequestParam Float lon) {
        try {
            String urlBicicleta = "http://localhost:8081/aparcamientos";
            ResponseEntity<Parking[]> response = restTemplate.getForEntity(urlBicicleta, Parking[].class);
            Parking[] aparcamientos = response.getBody();

            if (aparcamientos == null || aparcamientos.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay aparcamientos disponibles");
            }

            Parking masCercano = null;
            float distanciaMinima = Float.MAX_VALUE;

            for (Parking parking : aparcamientos) {
                String estadoUrl = "http://localhost:8081/aparcamiento/" + parking.getIdparking() + "/status";
                EstadoDTO estado = restTemplate.getForObject(estadoUrl, EstadoDTO.class);

                if (estado != null && estado.bikesAvailable() > 0) {
                    float distancia = calcularDistancia(lat, lon, parking.getLatitude(), parking.getLongitude());
                    if (distancia < distanciaMinima) {
                        distanciaMinima = distancia;
                        masCercano = parking;
                    }
                }
            }

            if (masCercano == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No hay aparcamientos con bicis disponibles");
            }

            return ResponseEntity.ok(masCercano);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error consultando el servicio de bicicletas");
        }
    }

    private float calcularDistancia(float lat1, float lon1, float lat2, float lon2) {
        final float R = 6371f;
        float dLat = (float) Math.toRadians(lat2 - lat1);
        float dLon = (float) Math.toRadians(lon2 - lon1);
        float a = (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        return R * c;
    }

}
