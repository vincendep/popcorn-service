package it.vincendep.popcorn.integration.tmdb.web;

import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class TmdbHandler {

    private final TmdbService service;

    public Mono<ServerResponse> getMovieDetails(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return ok().body(service.getMovieDetails(id), TmdbMovieResponse.class);
    }
}
