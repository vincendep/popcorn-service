package it.vincendep.popcorn.integration.tmdb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.web.reactive.function.client.ClientRequest.from;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Configuration
@RequiredArgsConstructor
public class TmdbConfig {

    private final TmdbProperties tmdbProperties;

    @Bean
    public WebClient tmdbV4Client(WebClient.Builder builder) {
        ExchangeFilterFunction requestTokenFilter = (request, next) ->
                next.exchange(from(request)
                        .headers(h -> h.setBearerAuth(tmdbProperties.getAccessToken()))
                        .build());
        return builder
                .baseUrl("https://api.themoviedb.org/4")
                .filter(requestTokenFilter)
                .build();
    }

    @Bean
    public WebClient tmdbV3Client(WebClient.Builder builder) {
        ExchangeFilterFunction apiKeyFilter = (clientRequest, nextFilter) ->
                nextFilter.exchange(from(clientRequest)
                        .url(fromUri(clientRequest.url())
                                .queryParam("api_key", tmdbProperties.getApiKey())
                                .build()
                                .toUri())
                        .build());
        return builder
                .baseUrl("https://api.themoviedb.org/3")
                .filter(apiKeyFilter)
                .build();
    }
}
