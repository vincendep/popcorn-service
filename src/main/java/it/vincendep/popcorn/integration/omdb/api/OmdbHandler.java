package it.vincendep.popcorn.integration.omdb.api;

import it.vincendep.popcorn.integration.omdb.dto.OmdbMovieResponse;
import it.vincendep.popcorn.integration.omdb.service.OmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class OmdbHandler {

    private final OmdbService service;

    public Mono<ServerResponse> getMovieDetails(ServerRequest request) {
        String imdbId = request.pathVariable("imdbId");
        return ok().body(service.getById(imdbId), OmdbMovieResponse.class);
    }
}
