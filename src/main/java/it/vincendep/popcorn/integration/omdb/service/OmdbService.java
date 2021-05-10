package it.vincendep.popcorn.integration.omdb.service;

import it.vincendep.popcorn.integration.omdb.dto.OmdbMovieResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OmdbService {

    private final WebClient omdbClient;

    public OmdbService(@Qualifier("omdb-client") WebClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    public Mono<OmdbMovieResponse> getMovieDetails(String imdbId) {
        return null;
    }
}
