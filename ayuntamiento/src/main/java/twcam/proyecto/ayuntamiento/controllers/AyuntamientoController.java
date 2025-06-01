package twcam.proyecto.ayuntamiento.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import twcam.proyecto.shared.Estacion;
import twcam.proyecto.shared.EstadoDTO;
import twcam.proyecto.shared.Parking;

@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@RestController
public class AyuntamientoController {

    private final RestTemplate restTemplate;

    public AyuntamientoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/estacion")
    @Operation(summary = "Crea una estación", description = "Crea una estación redirigiendo la petición al microservicio 'polucion'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Estación creada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "409", description = "Ya existe una estación con el id indicado")
    public ResponseEntity<?> crearEstacion(@RequestBody Estacion estacion,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String urlPolucion = "http://localhost:8082/estacion";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Estacion> request = new HttpEntity<>(estacion, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(urlPolucion, request, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con polucion-service");
        }
    }

    @DeleteMapping("/estacion/{id}")
    @Operation(summary = "Elimina una estación", description = "Elimina una estación redirigiendo la petición al microservicio 'polucion'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Estación eliminada correctamente")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No se encontró la estación con ese id")
    public ResponseEntity<?> eliminarEstacion(@PathVariable String id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String urlPolucion = "http://localhost:8082/estacion/" + id;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<Void> response = restTemplate.exchange(urlPolucion, HttpMethod.DELETE, request, Void.class);

            return ResponseEntity.status(response.getStatusCode())
                    .body("Estación con id " + id + " eliminada correctamente");
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la estación con id " + id);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con polucion-service");
        }
    }

    @PutMapping("/estacion/{id}")
    @Operation(summary = "Modifica una estación", description = "Modifica una estación redirigiendo la petición al microservicio 'polucion'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Estación actualizada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios en la petición")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No existe una estación con el id indicado")
    public ResponseEntity<?> modificarEstacion(@PathVariable String id, @RequestBody Estacion estacion,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String urlPolucion = "http://localhost:8082/estacion/" + id;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Estacion> request = new HttpEntity<>(estacion, headers);

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
    @Operation(summary = "Crea un aparcamiento", description = "Crea un aparcamiento redirigiendo la petición al microservicio 'bicicletas'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Aparcamiento creado correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "409", description = "Ya existe un aparcamiento con el id indicado")
    public ResponseEntity<?> crearParking(@RequestBody Parking parking,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String urlBicicleta = "http://localhost:8081/aparcamiento";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Parking> request = new HttpEntity<>(parking, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(urlBicicleta, request, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con bicicletas-service");
        }
    }

    @DeleteMapping("/aparcamiento/{id}")
    @Operation(summary = "Elimina un parking", description = "Elimina un parking redirigiendo la petición al microservicio 'bicicletas'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Parking eliminado correctamente")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No se encontró el parking con ese id")
    public ResponseEntity<?> eliminarParking(@PathVariable String id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String urlBicicleta = "http://localhost:8081/aparcamiento/" + id;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Void> request = new HttpEntity<>(headers);

            ResponseEntity<Void> response = restTemplate.exchange(urlBicicleta, HttpMethod.DELETE, request, Void.class);

            return ResponseEntity.status(response.getStatusCode())
                    .body("Parking con id " + id + " eliminado correctamente");
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe el parking con id " + id);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con bicicletas-service");
        }
    }

    @PutMapping("/aparcamiento/{id}")
    @Operation(summary = "Modifica un parking", description = "Modifica una parking redirigiendo la petición al microservicio 'bicicletas'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Parking actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios en la petición")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No existe una parking con el id indicado")
    public ResponseEntity<?> modificarParking(@PathVariable String id, @RequestBody Parking parking,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String urlBicicleta = "http://localhost:8081/aparcamiento/" + id;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Parking> request = new HttpEntity<>(parking, headers);

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
    @Operation(summary = "Aparcamiento más cercano con bicis disponibles", description = "Devuelve el más cercano a la posición indicada", tags = {
            "Operaciones públicas" })
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

    @GetMapping("/estacionCercana")
    @Operation(summary = "Obtiene la estación más cercana", description = "Devuelve la estación más cercana a la posición indicada (lat/lon)", tags = {
            "Operaciones públicas" })
    @ApiResponse(responseCode = "200", description = "Se ha encontrado una estación de medición cercana")
    @ApiResponse(responseCode = "404", description = "No hay estaciones disponibles")
    @ApiResponse(responseCode = "500", description = "Error de comunicación con el servicio de polución")
    public ResponseEntity<?> estacionCercana(@RequestParam Float lat,
            @RequestParam Float lon) {
        try {
            String urlPolucion = "http://localhost:8082/estaciones";
            ResponseEntity<Estacion[]> response = restTemplate.getForEntity(urlPolucion, Estacion[].class);
            Estacion[] estaciones = response.getBody();

            if (estaciones == null || estaciones.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay estaciones disponibles");
            }

            Estacion masCercana = null;
            float distanciaMinima = Float.MAX_VALUE;

            for (Estacion estacion : estaciones) {
                float distancia = calcularDistancia(lat, lon, estacion.getLatitud(), estacion.getLongitud());
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    masCercana = estacion;
                }
            }

            if (masCercana == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró una estación cercana");
            }

            return ResponseEntity.ok(masCercana);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error consultando el servicio de polución");
        }
    }

    /**
     * Función auxiliar que calcula la distancia entre dos puntos dados
     * 
     * @param lat1 Latitud 1
     * @param lon1 Longitud 1
     * @param lat2 Latitud 2
     * @param lon2 Longitud 2
     * @return Distancia entre los dos puntos
     */
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
