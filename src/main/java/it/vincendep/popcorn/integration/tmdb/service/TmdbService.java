package it.vincendep.popcorn.integration.tmdb.service;

import it.vincendep.popcorn.util.ArrayUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import it.vincendep.popcorn.integration.tmdb.dto.AppendToResponse;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbExternalIdResponse;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class TmdbService {

    private final WebClient tmdbClient;

    public TmdbService(@Qualifier("tmdbV3Client") WebClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public Mono<TmdbMovieResponse> getMovieDetails(Long movieId, AppendToResponse... appendToResponses) {
        return tmdbClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParamIfPresent("append_to_response", Optional.of(appendToResponses)
                                .filter(ArrayUtils::isNotEmpty)
                                .map(AppendToResponse::queryString))
                        .build(movieId))
                .retrieve()
                .bodyToMono(TmdbMovieResponse.class);
    }
    
    public Mono<TmdbExternalIdResponse> getMovieExternalIds(Long movieId) {
    	return tmdbClient.get()
    			.uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}/external_ids")
    					.build(movieId))
    			.retrieve()
    			.bodyToMono(TmdbExternalIdResponse.class);
    }
}
