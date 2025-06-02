package twcam.proyecto.ayuntamiento.controllers;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import twcam.proyecto.ayuntamiento.service.AyuntamientoService;
import twcam.proyecto.shared.Estacion;
import twcam.proyecto.shared.Parking;

@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@RestController
public class AyuntamientoController {

    private final AyuntamientoService ayuntamientoService;

    public AyuntamientoController(AyuntamientoService ayuntamientoService) {
        this.ayuntamientoService = ayuntamientoService;
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
        return ayuntamientoService.crearEstacion(estacion, authHeader);
    }

    @DeleteMapping("/estacion/{id}")
    @Operation(summary = "Elimina una estación", description = "Elimina una estación redirigiendo la petición al microservicio 'polucion'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Estación eliminada correctamente")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No se encontró la estación con ese id")
    public ResponseEntity<?> eliminarEstacion(@PathVariable String id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        return ayuntamientoService.eliminarEstacion(id, authHeader);
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
        return ayuntamientoService.modificarEstacion(id, estacion, authHeader);
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
        return ayuntamientoService.crearParking(parking, authHeader);
    }

    @DeleteMapping("/aparcamiento/{id}")
    @Operation(summary = "Elimina un parking", description = "Elimina un parking redirigiendo la petición al microservicio 'bicicletas'", tags = {
            "Operaciones que necesitan el rol 'admin'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Parking eliminado correctamente")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "No se encontró el parking con ese id")
    public ResponseEntity<?> eliminarParking(@PathVariable String id,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
        return ayuntamientoService.eliminarParking(id, authHeader);
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
        return ayuntamientoService.modificarParking(id, parking, authHeader);
    }

    @GetMapping("/aparcamientoCercano")
    @Operation(summary = "Aparcamiento más cercano con bicis disponibles", description = "Devuelve el más cercano a la posición indicada", tags = {
            "Operaciones públicas" })
    @ApiResponse(responseCode = "200", description = "Se ha encontrado un aparcamiento disponible cercano")
    @ApiResponse(responseCode = "404", description = "No hay aparcamientos disponibles o con bicis cerca de la ubicación dada")
    @ApiResponse(responseCode = "500", description = "Error de comunicación con el servicio de bicicletas")
    public ResponseEntity<?> aparcamientoCercano(@RequestParam Float lat,
            @RequestParam Float lon) {
        return ayuntamientoService.aparcamientoCercano(lat, lon);
    }

    @GetMapping("/estacionCercana")
    @Operation(summary = "Obtiene la estación más cercana", description = "Devuelve la estación más cercana a la posición indicada (lat/lon)", tags = {
            "Operaciones públicas" })
    @ApiResponse(responseCode = "200", description = "Se ha encontrado una estación de medición cercana")
    @ApiResponse(responseCode = "404", description = "No hay estaciones disponibles")
    @ApiResponse(responseCode = "500", description = "Error de comunicación con el servicio de polución")
    public ResponseEntity<?> estacionCercana(@RequestParam Float lat,
            @RequestParam Float lon) {
        return ayuntamientoService.estacionCercana(lat, lon);
    }

}
