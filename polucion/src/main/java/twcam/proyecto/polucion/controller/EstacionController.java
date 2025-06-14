package twcam.proyecto.polucion.controller;

import java.util.List;
import java.util.NoSuchElementException;

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
import twcam.proyecto.polucion.service.EstacionService;
import twcam.proyecto.shared.Estacion;

@RestController
public class EstacionController {

    private final EstacionService estacionService;

    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    @PostMapping("/estacion")
    @Operation(summary = "Añade una nueva estación de medición", description = "Permite al administrador registrar una nueva estación de medición con su id, dirección, latitud y longitud", tags = {
            "Operaciones accesibles desde la API del Ayuntamiento" })
    @ApiResponse(responseCode = "201", description = "Estación creada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios como 'id' o 'dirección'")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "409", description = "Ya existe una estación con el id indicado")
    public ResponseEntity<?> crearEstacion(@RequestBody Estacion estacion) {
        try {
            Estacion nueva = estacionService.crearEstacion(estacion);

            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/estacion/{id}")
    @Operation(summary = "Elimina una estación de medición", description = "Elimina una estación de medición de la base de datos según su id", tags = {
            "Operaciones accesibles desde la API del Ayuntamiento" })
    @ApiResponse(responseCode = "200", description = "Estación eliminada correctamente")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No se encontró la estación con ese id")
    public ResponseEntity<?> eliminarEstacion(@PathVariable String id) {
        try {
            estacionService.eliminarEstacion(id);

            return ResponseEntity.ok("Estación con id " + id + " eliminada correctamente");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/estacion/{id}")
    @Operation(summary = "Modifica una estación existente", description = "Actualiza los datos (dirección, latitud y longitud) de una estación de medición identificada por su id", tags = {
            "Operaciones accesibles desde la API del Ayuntamiento" })
    @ApiResponse(responseCode = "200", description = "Estación actualizada correctamente")
    @ApiResponse(responseCode = "400", description = "Faltan campos obligatorios en la petición")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No existe una estación con el id indicado")
    public ResponseEntity<?> actualizarEstacion(@PathVariable String id, @RequestBody Estacion estacion) {
        try {
            Estacion actualizada = estacionService.actualizarEstacion(id, estacion);

            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Obtiene todas las estaciones", description = "Obtiene un listado con todas las estaciones de medición de la aplicación junto con sus datos", tags = {
            "Operaciones públicas" })
    @ApiResponse(responseCode = "200", description = "Devuelve el listado de estaciones")
    @GetMapping("/estaciones")
    public List<Estacion> getAllEstaciones() {
        return estacionService.obtenerTodas();
    }
}