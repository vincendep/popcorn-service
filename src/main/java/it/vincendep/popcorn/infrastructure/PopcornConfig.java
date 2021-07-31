package it.vincendep.popcorn.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PopcornConfig {

	@Bean
	@Primary
	public WebClient webClient() {
		return WebClient.create();
	}
}
