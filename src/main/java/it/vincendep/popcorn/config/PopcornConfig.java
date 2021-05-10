package it.vincendep.popcorn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.function.client.WebClient;

import it.vincendep.popcorn.PopcornApplication;

import static it.vincendep.popcorn.config.Profile.DEVELOPMENT;

@Configuration
public class PopcornConfig {

	@Bean
	@Primary
	public WebClient webClient() {
		return WebClient.create();
	}
}
