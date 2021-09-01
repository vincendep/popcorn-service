package it.vincendep.popcorn.integration.tmdb.service;

import it.vincendep.popcorn.integration.tmdb.dto.*;
import it.vincendep.popcorn.util.ArrayUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class TmdbService {

    private final WebClient tmdbClient;

    public TmdbService(@Qualifier("tmdbV3Client") WebClient tmdbClient) {
        this.tmdbClient = tmdbClient;
    }

    public Mono<TmdbAppendableResponse<TmdbMovieResponse>> getMovieDetails(Long movieId, AppendToResponse... appendToResponses) {
        return tmdbClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}")
                        .queryParamIfPresent("append_to_response", Optional.of(appendToResponses)
                                .filter(ArrayUtils::isNotEmpty)
                                .map(AppendToResponse::queryString))
                        .build(movieId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<TmdbExternalIdResponse> getMovieExternalIds(Long movieId) {
        return tmdbClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/movie/{id}/external_ids")
                        .build(movieId))
                .retrieve()
                .bodyToMono(TmdbExternalIdResponse.class);
    }

    public Mono<TmdbAppendableResponse<TmdbTvShowResponse>> getTvShowDetails(Long tvShowId, AppendToResponse... appendToResponses) {
        return tmdbClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tv/{id}")
                        .queryParamIfPresent("append_to_response", Optional.of(appendToResponses)
                                .filter(ArrayUtils::isNotEmpty)
                                .map(AppendToResponse::queryString))
                        .build(tvShowId))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
