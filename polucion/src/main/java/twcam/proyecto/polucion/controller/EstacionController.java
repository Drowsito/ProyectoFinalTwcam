package twcam.proyecto.polucion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import twcam.proyecto.polucion.model.Estacion;
import twcam.proyecto.polucion.repository.EstacionRepository;

@RestController
public class EstacionController {

    private final EstacionRepository estacionRepository;

    public EstacionController(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    // TODO: Añadir seguridad: rol "admin" (al OpenAPI también?)
    @PostMapping("/estacion")
    @Operation(summary = "Añade una nueva estación de medición", description = "Permite al administrador registrar una nueva estación de medición con su id, dirección, latitud y longitud")
    @ApiResponse(responseCode = "201", description = "Estación creada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "409", description = "Ya existe una estación con el id indicado")
    public ResponseEntity<?> crearEstacion(@RequestBody Estacion estacion) {
        // Valida los campos del request
        if (estacion.getId() == null || estacion.getId().isBlank()) {
            return ResponseEntity.badRequest().body("Falta introducir el campo 'id'");
        } else if (estacion.getDireccion() == null || estacion.getDireccion().isBlank()) {
            return ResponseEntity.badRequest().body("Falta introducir el campo 'direccion'");
        }

        // Comprueba si ya existe una estación con ese id
        if (estacionRepository.existsById(estacion.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe una estación con id " + estacion.getId());
        }

        Estacion nueva = estacionRepository.save(estacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @Operation(summary = "Obtiene todas las estaciones", description = "Obtiene un listado con todas las estaciones de medición de la aplicación junto con sus datos")
    @ApiResponse(responseCode = "200", description = "Devuelve el listado de estaciones")
    @GetMapping("/estaciones")
    public List<Estacion> getAllEstaciones() {
        return estacionRepository.findAll();
    }
}