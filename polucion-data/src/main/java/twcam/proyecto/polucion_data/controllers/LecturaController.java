package twcam.proyecto.polucion_data.controllers;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.polucion_data.model.Lectura;
import twcam.proyecto.polucion_data.repository.LecturaRepository;

@RestController
@RequestMapping("/lectura")
public class LecturaController {

    private final LecturaRepository lecturaRepository;

    @GetMapping
    public List<Lectura> getAll() {
        return lecturaRepository.findAll();
    }

    public LecturaController(LecturaRepository lecturaRepository) {
        this.lecturaRepository = lecturaRepository;
    }

    @PostMapping
    public Lectura create(@RequestBody Lectura lectura) {
        return lecturaRepository.save(lectura);
    }

    @GetMapping("/by-estacion/{id}")
    public ResponseEntity<List<Lectura>> findByIdOrderByTimeStampDesc(@PathVariable int id) {
        List<Lectura> lecturas = lecturaRepository.findByIdOrderByTimeStampDesc(id);
        return ResponseEntity.ok(lecturas);
    }

    @GetMapping("/by-estacion/{id}/range")
    public ResponseEntity<List<Lectura>> findByIdAndTimeStampBetweenOrderByTimeStampDesc(
            @PathVariable int id,
            @RequestParam String from,
            @RequestParam String to) {

        Instant fromInstant = Instant.parse(from);
        Instant toInstant = Instant.parse(to);

        List<Lectura> lecturas = lecturaRepository.findByIdAndTimeStampBetweenOrderByTimeStampDesc(id, fromInstant,
                toInstant);
        return ResponseEntity.ok(lecturas);
    }

}
