package it.vincendep.popcorn.integration.omdb.service;

import it.vincendep.popcorn.integration.omdb.dto.OmdbMovieResponse;
import it.vincendep.popcorn.integration.omdb.exception.OmdbServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.bool.BooleanUtils;
import reactor.core.publisher.Mono;

@Service
public class OmdbService {

    private final WebClient omdbClient;

    public OmdbService(@Qualifier("omdbClient") WebClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    public Mono<OmdbMovieResponse> getById(String imdbId) {
        return omdbClient.get()
                .uri(builder -> builder.queryParam("i", imdbId).build())
                .retrieve()
                .bodyToMono(OmdbMovieResponse.class)
                .handle((res, sink) -> {
                    if (res.getResponse()) {
                        sink.next(res);
                    } else {
                        sink.error(new OmdbServiceException("Error getting OmdbMovie with id=" + imdbId + ": " + res.getError()));
                    }
                });
    }
}
