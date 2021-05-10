package it.vincendep.popcorn.integration.omdb.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "omdb")
public class OmdbProperties {

    @Value("${OMDB_API_KEY}")
    private String apiKey;
    private String apiHost;
}
