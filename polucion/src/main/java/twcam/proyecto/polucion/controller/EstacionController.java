package twcam.proyecto.polucion.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.polucion.model.Estacion;
import twcam.proyecto.polucion.repository.EstacionRepository;

@RestController
@RequestMapping("/estaciones")
public class EstacionController {

    private final EstacionRepository estacionRepository;

    public EstacionController(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @GetMapping
    public List<Estacion> getAllEstaciones() {
        return estacionRepository.findAll();
    }
}