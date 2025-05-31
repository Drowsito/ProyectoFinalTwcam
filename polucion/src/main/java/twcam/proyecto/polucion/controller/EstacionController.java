package twcam.proyecto.polucion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import twcam.proyecto.poluciondata.model.Estacion;
import twcam.proyecto.poluciondata.repository.EstacionRepository;

@RestController
public class EstacionController {

    private final EstacionRepository estacionRepository;

    public EstacionController(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @PostMapping("/estacion")
    @Operation(summary = "Añade una nueva estación de medición", description = "Permite al administrador registrar una nueva estación de medición con su id, dirección, latitud y longitud", tags = {
            "Operaciones accesibles desde la API del Ayuntamiento" })
    @ApiResponse(responseCode = "201", description = "Estación creada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "409", description = "Ya existe una estación con el id indicado")
    public ResponseEntity<?> crearEstacion(@RequestBody Estacion estacion) {
        // Valida si se han introducido el id o la dirección en el request
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

    @DeleteMapping("/estacion/{id}")
    @Operation(summary = "Elimina una estación de medición", description = "Elimina una estación de medición de la base de datos según su id", tags = {
            "Operaciones accesibles desde la API del Ayuntamiento" })
    @ApiResponse(responseCode = "200", description = "Estación eliminada correctamente")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No se encontró la estación con ese id")
    public ResponseEntity<?> eliminarEstacion(@PathVariable String id) {
        // Comprueba si existe una estación con ese id
        if (!estacionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe ninguna estación con id " + id);
        }

        estacionRepository.deleteById(id);

        return ResponseEntity.ok("Estación con id " + id + " eliminada correctamente");
    }

    @PutMapping("/estacion/{id}")
    @Operation(summary = "Modifica una estación existente", description = "Actualiza los datos (dirección, latitud y longitud) de una estación de medición identificada por su id", tags = {
            "Operaciones accesibles desde la API del Ayuntamiento" })
    @ApiResponse(responseCode = "200", description = "Estación actualizada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios en la petición")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No existe una estación con el id indicado")
    public ResponseEntity<?> actualizarEstacion(@PathVariable String id, @RequestBody Estacion estacion) {
        // Comprueba si existe una estación con ese id
        if (!estacionRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe ninguna estación con id " + id);
        }

        // Valida si se ha introducido la dirección
        if (estacion.getDireccion() == null || estacion.getDireccion().isBlank()) {
            return ResponseEntity.badRequest().body("Falta introducir el campo 'direccion'");
        }

        Estacion estacionBD = estacionRepository.findById(id).get();
        estacionBD.setDireccion(estacion.getDireccion());
        estacionBD.setLatitud(estacion.getLatitud());
        estacionBD.setLongitud(estacion.getLongitud());
        estacionRepository.save(estacionBD);

        return ResponseEntity.ok(estacionBD);
    }

    @Operation(summary = "Obtiene todas las estaciones", description = "Obtiene un listado con todas las estaciones de medición de la aplicación junto con sus datos", tags = {
            "Operaciones públicas" })
    @ApiResponse(responseCode = "200", description = "Devuelve el listado de estaciones")
    @GetMapping("/estaciones")
    public List<Estacion> getAllEstaciones() {
        return estacionRepository.findAll();
    }
}