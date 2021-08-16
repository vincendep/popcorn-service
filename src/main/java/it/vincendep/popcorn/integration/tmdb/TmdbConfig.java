package it.vincendep.popcorn.integration.tmdb;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.web.reactive.function.client.ClientRequest.from;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Configuration
@ComponentScan
@RequiredArgsConstructor
public class TmdbConfig {

    @Bean
    @ConditionalOnProperty("TMDB_ACCESS_TOKEN")
    public WebClient tmdbV4Client(WebClient.Builder builder, @Value("${TMDB_ACCESS_TOKEN}") String accessToken) {
        ExchangeFilterFunction requestTokenFilter = (request, next) ->
                next.exchange(from(request)
                        .headers(h -> h.setBearerAuth(accessToken))
                        .build());
        return builder
                .baseUrl("https://api.themoviedb.org/4")
                .filter(requestTokenFilter)
                .build();
    }

    @Bean
    public WebClient tmdbV3Client(WebClient.Builder builder, @Value("${TMDB_API_KEY}") String apiKey) {
        ExchangeFilterFunction apiKeyFilter = (clientRequest, next) ->
                next.exchange(from(clientRequest)
                        .url(fromUri(clientRequest.url())
                                .queryParam("api_key", apiKey)
                                .build()
                                .toUri())
                        .build());
        return builder
                .baseUrl("https://api.themoviedb.org/3")
                .filter(apiKeyFilter)
                .build();
    }
}
