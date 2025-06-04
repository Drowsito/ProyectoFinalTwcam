package twcam.proyecto.polucion.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import twcam.proyecto.polucion.service.LecturaService;
import twcam.proyecto.shared.Lectura;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@RestController
public class LecturaController {

    private final LecturaService lecturaService;

    public LecturaController(LecturaService lecturaService) {
        this.lecturaService = lecturaService;
    }

    @PostMapping("/estacion/{id}")
    @Operation(summary = "Registra una nueva lectura de sensores", description = "Guarda una lectura de calidad del aire enviada por una estación de medición", tags = {
            "Operaciones que necesitan el rol 'estacion'" }, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Lectura registrada correctamente")
    @ApiResponse(responseCode = "400", description = "Petición mal escrita")
    @ApiResponse(responseCode = "401", description = "Sin permisos necesarios para esta petición")
    @ApiResponse(responseCode = "404", description = "La estación con ese id no existe")
    public ResponseEntity<?> registrarLectura(@PathVariable String id, @RequestBody Lectura lectura) {
        try {
            Lectura nueva = lecturaService.registrarLectura(id, lectura);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/estacion/{id}/status")
    @Operation(summary = "Obtener la última lectura o las lecturas por intervalo", description = "Devuelve la última lectura de una estación o todas las lecturas en un intervalo si se pasan parámetros 'from' y 'to'", tags = {
            "Operaciones públicas" })
    @ApiResponse(responseCode = "200", description = "Lecturas devueltas correctamente")
    @ApiResponse(responseCode = "400", description = "Fechas mal escritas")
    @ApiResponse(responseCode = "404", description = "No se encontraron lecturas")
    public ResponseEntity<?> obtenerLecturas(
            @PathVariable int id,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {

        try {
            if (from != null && to != null) {
                List<Lectura> lecturas = lecturaService.obtenerLecturasEnIntervalo(id, from, to);

                return ResponseEntity.ok(lecturas);
            } else {
                Lectura ultimaLectura = lecturaService.obtenerUltimaLectura(id);

                return ResponseEntity.ok(ultimaLectura);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
