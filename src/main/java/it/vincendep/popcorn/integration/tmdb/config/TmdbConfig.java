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

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:config/tmdb.properties")
@EnableConfigurationProperties(TmdbProperties.class)
public class TmdbConfig {

    private final TmdbProperties tmdbProperties;

    @Bean("tmdb-file")
    public WebClient tmdbFileClient(WebClient.Builder builder) {
        return builder.baseUrl(tmdbProperties.getFileHost()).build();
    }

    @Bean("tmdb-v4")
    public WebClient tmdbV4Client(WebClient.Builder builder) {
        ExchangeFilterFunction requestTokenFilter = (request, next) ->
                next.exchange(ClientRequest.from(request)
                        .headers(h -> h.setBearerAuth(tmdbProperties.getAccessToken()))
                        .build());
        return builder
                .baseUrl(tmdbProperties.getApiHost() + "/4")
                .filter(requestTokenFilter)
                .build();
    }

    @Bean("tmdb-v3")
    public WebClient tmdbV3Client(WebClient.Builder builder) {
        ExchangeFilterFunction apiKeyFilter = (clientRequest, nextFilter) ->
                nextFilter.exchange(ClientRequest.from(clientRequest)
                        .url(UriComponentsBuilder
                                .fromUri(clientRequest.url())
                                .queryParam("api_key", tmdbProperties.getApiKey())
                                .build()
                                .toUri())
                        .build());
        return builder
                .baseUrl(tmdbProperties.getApiHost() + "/3")
                .filter(apiKeyFilter)
                .build();
    }
}
