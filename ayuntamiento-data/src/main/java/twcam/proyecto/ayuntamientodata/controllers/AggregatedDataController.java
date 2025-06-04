package twcam.proyecto.ayuntamientodata.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import twcam.proyecto.ayuntamientodata.model.AggregatedData;
import twcam.proyecto.ayuntamientodata.repository.AggregatedDataRepository;

@RestController
@RequestMapping("/aggregated-data")
public class AggregatedDataController {

    private final AggregatedDataRepository repository;

    public AggregatedDataController(AggregatedDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/last")
    public AggregatedData findLast() {
        return repository.findFirstByOrderByTimeStampDesc();
    }

    @PostMapping
    public AggregatedData save(@RequestBody AggregatedData data) {
        return repository.save(data);
    }
}
