package twcam.proyecto.ayuntamiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@EnableFeignClients
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API de ayuntamiento", version = "v1", contact = @Contact(name = "Pablo Gómez/Diego Ruiz", email = "pagobo2@alumni.uv.es/dieruiz4@alumni.uv.es"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), description = "API que contiene todas las operaciones necesarias para la gestión del ayuntamiento"), servers = @Server(url = "/", description = "Production"))
public class AyuntamientoApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(AyuntamientoApplication.class, args);
	}

}
