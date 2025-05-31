package twcam.proyecto.ayuntamiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import twcam.proyecto.ayuntamientodata.imports.ImportServiceMongo;

@SpringBootApplication(scanBasePackages = {
		"twcam.proyecto.ayuntamiento",
		"twcam.proyecto.ayuntamientodata"
})
@EnableMongoRepositories(basePackages = "twcam.proyecto.ayuntamientodata.repository")
@EntityScan(basePackages = "twcam.proyecto.ayuntamientodata.model")
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "API de ayuntamiento", version = "v1", contact = @Contact(name = "Pablo Gómez/Diego Ruiz", email = "pagobo2@alumni.uv.es/dieruiz4@alumni.uv.es"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), description = "API que contiene todas las operaciones necesarias para la gestión del ayuntamiento"), servers = @Server(url = "/", description = "Production"))
public class AyuntamientoApplication implements CommandLineRunner {

	@Autowired
	private ImportServiceMongo importServiceMongo;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(AyuntamientoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		importServiceMongo.importarDatos(new ClassPathResource("aggregatedData.txt"));
	}

}
