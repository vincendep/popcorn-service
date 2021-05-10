package it.vincendep.popcorn.integration.tmdb.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.vincendep.popcorn.integration.tmdb.dto.AppendToResponse;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbExternalIdResponse;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import reactor.core.publisher.Mono;

@Service
public class TmdbService {

    private final WebClient tmdbClient;

    public TmdbService(@Qualifier("tmdb-v3") WebClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public Mono<TmdbMovieResponse> getMovieDetails(Long movieId, AppendToResponse... appendToResponses) {
        return tmdbClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParam("append_to_response", AppendToResponse.queryString(appendToResponses))
                        .build(movieId))
                .retrieve()
                .bodyToMono(TmdbMovieResponse.class);
    }
    
    public Mono<TmdbExternalIdResponse> getMovieExternalIds(Long movieId) {
    	return tmdbClient.get()
    			.uri(uriBuilder -> uriBuilder.
    					path("/movie/{id}/external_ids")
    					.build(movieId))
    			.retrieve()
    			.bodyToMono(TmdbExternalIdResponse.class);
    }
}
