package br.com.ferreiradev.fmu.core;

import br.com.ferreiradev.fmu.core.application.repository.FishRepository;
import br.com.ferreiradev.fmu.core.domain.model.Fish;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);

        builder.bannerMode(Banner.Mode.OFF);
        builder.profiles("sandbox");

        builder.run(args);

	}

    @Bean
    CommandLineRunner run(FishRepository repo) {
        return args -> {
            repo.save(new Fish("Tambaqui"));
            repo.save(new Fish("Pintado"));
            repo.save(new Fish("Pirarara"));

            repo.findAll().forEach(f -> System.out.println(f.getId() + " -> " + f.getName()));
        };
    }

}
