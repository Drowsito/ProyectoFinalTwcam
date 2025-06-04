package twcam.proyecto.polucion_data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import twcam.proyecto.polucion_data.imports.ImportServiceMongo;

@SpringBootApplication
public class PolucionDataApplication implements CommandLineRunner {
	@Autowired
	private ImportServiceMongo importServiceMongo;

	public static void main(String[] args) {
		SpringApplication.run(PolucionDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		importServiceMongo.importarLecturas(new ClassPathResource("lecturas.txt"));
	}

}
