package it.vincendep.popcorn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PopcornApplication {

	public static final String DEV = "dev";

	public static void main(String[] args) {
		SpringApplication.run(PopcornApplication.class, args);
	}

	@Bean
	@Primary
	public WebClient webClient() {
		return WebClient.create();
	}
}
