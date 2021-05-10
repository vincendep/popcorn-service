package it.vincendep.popcorn.integration.tmdb.config;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Data
@ConfigurationProperties(prefix = "tmdb")
public class TmdbProperties {

    @Value("${TMDB_API_KEY}")
    private String apiKey;
    private String host;
    private String apiHost;
    private String fileHost;
    private String accessToken;
    private String sessionId;
}
