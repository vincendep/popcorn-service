package it.vincendep.popcorn.integration.omdb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OmdbProperties {

    @Value("${OMDB_API_KEY}")
    private String apiKey;
}
