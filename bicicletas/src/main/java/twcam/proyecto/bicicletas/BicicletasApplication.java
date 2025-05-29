package twcam.proyecto.bicicletas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "twcam.proyecto.bicicletasdata.repository")
@EnableMongoRepositories(basePackages = "twcam.proyecto.bicicletasdata.repository")
@EntityScan(basePackages = "twcam.proyecto.bicicletasdata.model")
@OpenAPIDefinition(
  info =@Info(
    title = "API de bicicletas",
    version = "v1",
    contact = @Contact(
      name = "Pablo Gómez/Diego Ruiz", email = "pagobo2@alumni.uv.es/dieruiz4@alumni.uv.es"
    ),
    license = @License(
      name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    description = "API que contiene todas las operaciones necesarias para la gestión de las bicicletas"
  ),
  servers = @Server(
    url = "${api.server.url}",
    description = "Production"
  )
)
public class BicicletasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BicicletasApplication.class, args);
	}

}
