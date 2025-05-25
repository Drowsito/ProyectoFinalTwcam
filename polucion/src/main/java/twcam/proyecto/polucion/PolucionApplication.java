package twcam.proyecto.polucion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API de polución", version = "v1", contact = @Contact(name = "Pablo Gómez/Diego Ruiz", email = "pagobo2@alumni.uv.es/dieruiz4@alumni.uv.es"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), description = "API que contiene todas las operaciones necesarias para la gestión de las estaciones de medición de polución"), servers = @Server(url = "/", description = "Production"))
public class PolucionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolucionApplication.class, args);
	}
}
