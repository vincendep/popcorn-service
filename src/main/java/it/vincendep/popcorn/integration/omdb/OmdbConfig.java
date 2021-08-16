package it.vincendep.popcorn.integration.omdb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Configuration
@ComponentScan
@RequiredArgsConstructor
public class OmdbConfig {

    @Value("${OMDB_API_KEY}")
    private String apiKey;

    @Bean
    public WebClient omdbClient(WebClient.Builder builder) {
        ExchangeFilterFunction apiKeyFilter = ((request, next) ->
           next.exchange(ClientRequest.from(request)
                   .url(fromUri(request.url())
                           .queryParam("apikey", apiKey)
                           .build()
                           .toUri())
                   .build()));
        return builder
                .baseUrl("https://www.omdbapi.com")
                .filter(apiKeyFilter)
                .build();
    }
}
