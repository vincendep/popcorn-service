package it.vincendep.popcorn.integration.tmdb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class TmdbProperties {

    @Value("${TMDB_API_KEY}")
    private String apiKey;
    @Value("${TMDB_ACCESS_TOKEN}")
    private String accessToken;
}
