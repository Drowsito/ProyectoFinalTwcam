package twcam.proyecto.ayuntamiento.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import twcam.proyecto.ayuntamiento.service.AggregatedDataService;
import twcam.proyecto.ayuntamientodata.model.mongo.AggregatedData;

@RestController
public class AggregatedDataController {

    private final AggregatedDataService service;

    public AggregatedDataController(AggregatedDataService service) {
        this.service = service;
    }

    @PostMapping("/aggregateData")
    @Operation(summary = "Obtiene datos de polución y de estaciones", description = "Obtiene el número medio de bicicletas disponibles y el número medio de cada tipo de contaminante atmosférico. Los datos de polución se obtienen de la estación más cercana a cada aparcamiento. Se invoca a intervalores regulares de tiempo y persiste en una base de datos NoSQL.")
    // TODO: Hacer los ApiResponse bien
    // @ApiResponse(responseCode = "201", description = "Estación creada
    // correctamente")
    public ResponseEntity<?> agregar() {
        AggregatedData resultado = service.generarDatos();
        if (resultado == null) {
            return ResponseEntity.badRequest().body("No se pudo generar el documento");
        }
        return ResponseEntity.ok(resultado);
    }
}