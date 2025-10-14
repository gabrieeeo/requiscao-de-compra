package me.gabriel.requisicaodecompra;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import me.gabriel.requisicaodecompra.service.UsuarioService;

@SpringBootApplication
public class RequisicaodecompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequisicaodecompraApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioService usuarioService) {
		return args -> {
			usuarioService.criarUsuarioAdmin();
		};
	}
}
