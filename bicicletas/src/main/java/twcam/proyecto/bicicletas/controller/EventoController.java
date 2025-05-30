package twcam.proyecto.bicicletas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import twcam.proyecto.bicicletasdata.model.Evento;
import twcam.proyecto.shared.OperacionDTO;
import twcam.proyecto.bicicletas.service.EventoService;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/evento")
public class EventoController {
    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    @Operation(summary = "Add evento", description = "Añade un nuevo evento a un aparcamiento determinado")
    @ApiResponse(responseCode = "201", description = "Evento creado correctamente")
    @ApiResponse(responseCode = "400", description = "Operación no válida")
    @ApiResponse(responseCode = "404", description = "No se encontró el aparcamiento")
    public ResponseEntity<?> crearEvento(@PathVariable String id, @RequestBody OperacionDTO operacionDTO) {
        String operacion = operacionDTO.operation();

        if (operacion == null || operacion.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body("El campo 'operation' no puede estar vacío.");
        }

        Evento ultimo = service.findParkingStatus(id);
        if (ultimo == null) {
            return ResponseEntity
                    .status(404)
                    .body("No se encontró el aparcamiento con id: " + id);
        }

        int disponibles = ultimo != null ? ultimo.getBikesAvailable() : 0;
        int huecos = ultimo != null ? ultimo.getFreeParkingSpots() : 0;

        switch (operacion.toLowerCase()) {
            case "aparcamiento" -> {
                disponibles += 1;
                huecos -= 1;
            }
            case "alquiler" -> {
                disponibles -= 1;
                huecos += 1;
            }
            case "reposición_múltiple" -> {
                disponibles += 2;
                huecos -= 2;
            }
            case "retirada_múltiple" -> {
                disponibles -= 2;
                huecos += 2;
            }
            default -> {
                return ResponseEntity
                        .badRequest()
                        .body("Operación no válida: " + operacion);
            }
        }

        Evento evento = new Evento();
        evento.setId(id);
        evento.setOperation(operacion);
        evento.setBikesAvailable(disponibles);
        evento.setFreeParkingSpots(huecos);
        evento.setTimestamp(LocalDateTime.now());

        Evento guardado = service.save(evento);

        return ResponseEntity
                .status(201)
                .body("Evento con id " + guardado.getMongoId() +" almacenado con exito");
    }

}
