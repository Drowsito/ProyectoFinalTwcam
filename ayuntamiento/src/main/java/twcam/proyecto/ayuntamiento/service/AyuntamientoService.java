package twcam.proyecto.ayuntamiento.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import twcam.proyecto.shared.Estacion;
import twcam.proyecto.shared.EstadoDTO;
import twcam.proyecto.shared.Parking;

@Service
public class AyuntamientoService {

    private final RestTemplate restTemplate;

    public AyuntamientoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Crea una estación
     * 
     * @param estacion   Estación a crear
     * @param authHeader Token para autenticación
     * 
     * @return Resultado
     */
    public ResponseEntity<String> crearEstacion(Estacion estacion, String authHeader) {
        String urlPolucion = "http://localhost:8082/estacion";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            HttpEntity<Estacion> request = new HttpEntity<>(estacion, headers);

            return restTemplate.postForEntity(urlPolucion, request, String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error de comunicación con polucion-service");
        }
    }

    /**
     * Elimina una estación
     * 
     * @param id         Estación a eliminar
     * @param authHeader Token para autenticación
     * 
     * @return Resultado
     */
    public ResponseEntity<?> eliminarEstacion(String id, String authHeader) {
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

    /**
     * Modifica una estación
     * 
     * @param id         Identificador de la estación a modificar
     * @param estacion   Datos de la estación para modificar
     * @param authHeader Token para autenticación
     * 
     * @return Resultado
     */
    public ResponseEntity<?> modificarEstacion(String id, Estacion estacion, String authHeader) {
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

    /**
     * Crea un parking
     * 
     * @param parking    Id del parking a crear
     * @param authHeader Token para autenticación
     * 
     * @return Resultado
     */
    public ResponseEntity<?> crearParking(Parking parking, String authHeader) {
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

    /**
     * Elimina un parking
     * 
     * @param id         Identificador del parking a eliminar
     * @param authHeader Token para autenticación
     * 
     * @return Resultado
     */
    public ResponseEntity<?> eliminarParking(String id, String authHeader) {
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

    /**
     * Modifica un parking
     * 
     * @param id         Identificador del parking a modificar
     * @param parking    Parking con la información para modificar
     * @param authHeader Token para autenticación
     * 
     * @return Resultado
     */
    public ResponseEntity<?> modificarParking(String id, Parking parking, String authHeader) {
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

    /**
     * Busca un aparcamiento cercano a una posición
     * 
     * @param lat Latitud
     * @param lon Longitud
     * 
     * @return Aparcamiento encontrado
     */
    public ResponseEntity<?> aparcamientoCercano(Float lat, Float lon) {
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

    /**
     * Busca una estación cercana a una posición
     * 
     * @param lat Latitud
     * @param lon Longitud
     * 
     * @return Estación encontrada
     */
    public ResponseEntity<?> estacionCercana(Float lat, Float lon) {
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