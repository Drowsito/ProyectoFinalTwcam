package twcam.proyecto.polucion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import twcam.proyecto.polucion.model.mongo.Lectura;
import twcam.proyecto.polucion.repository.LecturaRepository;

@RestController
public class LecturaController {

    private final LecturaRepository lecturaRepository;

    public LecturaController(LecturaRepository lecturaRepository) {
        this.lecturaRepository = lecturaRepository;
    }

    // TODO: Añadir seguridad: rol "estacion" (al OpenAPI también?)
    @PostMapping("/estacion/{id}")
    @Operation(summary = "Registra una nueva lectura de sensores", description = "Guarda una lectura de calidad del aire enviada por una estación de medición")
    @ApiResponse(responseCode = "201", description = "Lectura registrada correctamente")
    @ApiResponse(responseCode = "400", description = "Petición mal escrita")
    @ApiResponse(responseCode = "404", description = "La estación con ese id no existe")
    public ResponseEntity<?> registrarLectura(@PathVariable String id, @RequestBody Lectura lectura) {
        if (lectura.getTimeStamp() == null) {
            return ResponseEntity.badRequest().body("Falta el campo 'timeStamp'");
        }

        lectura.setEstacionId(Integer.parseInt(id));
        Lectura nueva = lecturaRepository.save(lectura);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

}
