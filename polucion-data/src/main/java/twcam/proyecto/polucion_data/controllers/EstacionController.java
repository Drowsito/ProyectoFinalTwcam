package twcam.proyecto.polucion_data.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.polucion_data.model.Estacion;
import twcam.proyecto.polucion_data.repository.EstacionRepository;

@RestController
@RequestMapping("/estacion")
public class EstacionController {

    private final EstacionRepository estacionRepository;

    public EstacionController(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @GetMapping
    public List<Estacion> getAll() {
        return estacionRepository.findAll();
    }

    @PostMapping
    public Estacion create(@RequestBody Estacion estacion) {
        return estacionRepository.save(estacion);
    }

    @PutMapping("/{id}")
    public Estacion update(@PathVariable String id, @RequestBody Estacion estacion) {
        Estacion existente = estacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe"));
        existente.setDireccion(estacion.getDireccion());
        existente.setLatitud(estacion.getLatitud());
        existente.setLongitud(estacion.getLongitud());

        return estacionRepository.save(existente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        estacionRepository.deleteById(id);
    }

    public ResponseEntity<Estacion> getById(@PathVariable String id) {
        Optional<Estacion> estacion = estacionRepository.findById(id);

        if (estacion.isPresent()) {
            return ResponseEntity.ok(estacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable String id) {
        boolean exists = estacionRepository.existsById(id);
        return ResponseEntity.ok(exists);
    }
}
