package twcam.proyecto.ayuntamientodata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import twcam.proyecto.ayuntamientodata.imports.ImportServiceMongo;

@SpringBootApplication
public class AyuntamientodataApplication implements CommandLineRunner {
	@Autowired
	private ImportServiceMongo importServiceMongo;

	public static void main(String[] args) {
		SpringApplication.run(AyuntamientodataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		importServiceMongo.importarDatos(new ClassPathResource("aggregatedData.txt"));
	}

}
