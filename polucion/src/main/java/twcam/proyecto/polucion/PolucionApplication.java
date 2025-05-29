package twcam.proyecto.polucion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import twcam.proyecto.poluciondata.imports.ImportServiceMongo;

@SpringBootApplication(scanBasePackages = {
		"twcam.proyecto.polucion",
		"twcam.proyecto.poluciondata"
})
@EnableJpaRepositories(basePackages = "twcam.proyecto.poluciondata.repository")
@EnableMongoRepositories(basePackages = "twcam.proyecto.poluciondata.repository")
@EntityScan(basePackages = "twcam.proyecto.poluciondata.model")
@OpenAPIDefinition(info = @Info(title = "API de polución", version = "v1", contact = @Contact(name = "Pablo Gómez/Diego Ruiz", email = "pagobo2@alumni.uv.es/dieruiz4@alumni.uv.es"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), description = "API que contiene todas las operaciones necesarias para la gestión de las estaciones de medición de polución"), servers = @Server(url = "/", description = "Production"))
public class PolucionApplication implements CommandLineRunner {

	@Autowired
	private ImportServiceMongo importServiceMongo;

	public static void main(String[] args) {
		SpringApplication.run(PolucionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		importServiceMongo.importarLecturas(new ClassPathResource("lecturas.txt"));
	}
}
