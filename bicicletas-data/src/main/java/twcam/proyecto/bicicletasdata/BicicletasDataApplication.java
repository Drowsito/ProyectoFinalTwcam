package twcam.proyecto.bicicletasdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import twcam.proyecto.bicicletasdata.imports.ImportServiceMongo;

@SpringBootApplication
public class BicicletasDataApplication implements CommandLineRunner {
	@Autowired
	private ImportServiceMongo importServiceMongo;

	public static void main(String[] args) {
		SpringApplication.run(BicicletasDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		importServiceMongo.importarEventos(new ClassPathResource("evento.txt"));
	}

}
