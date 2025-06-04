package twcam.proyecto.ayuntamiento.service.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import twcam.proyecto.shared.AggregatedData;

@FeignClient(name = "aggregated-data-client", url = "http://localhost:8089")
public interface AggregatedDataClient {
    @PostMapping("/aggregated-data")
    AggregatedData save(@RequestBody AggregatedData data);

    @GetMapping("/aggregated-data/last")
    AggregatedData findLast();

}
