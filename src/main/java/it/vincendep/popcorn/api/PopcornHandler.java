package it.vincendep.popcorn.api;

import it.vincendep.popcorn.api.response.PopcornMovieResponse;
import it.vincendep.popcorn.core.MovieRating;
import it.vincendep.popcorn.core.MovieRatingService;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PopcornHandler {

    private final MovieRatingService movieRatingService;
    private final TmdbService tmdbService;

    public Mono<ServerResponse> queryMovies(ServerRequest request) {
        return ServerResponse.ok().build();
    }

    public Mono<ServerResponse> getMovie(ServerRequest request) {
        String id = request.pathVariable("id");
        return tmdbService.getMovieDetails(Long.valueOf(id))
                .zipWith(movieRatingService.findById(id).defaultIfEmpty(new MovieRating()), PopcornMovieResponse::new)
                .transform(body -> ServerResponse.ok().body(body, PopcornMovieResponse.class));
    }
}
