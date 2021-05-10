package it.vincendep.popcorn.integration.omdb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:config/omdb.properties")
@EnableConfigurationProperties(OmdbProperties.class)
public class OmdbConfig {

    @Bean("omdb-client")
    public WebClient omdbClient(WebClient.Builder builder) {
        return builder.build();
    }
}
