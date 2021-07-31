package it.vincendep.popcorn.api;

import it.vincendep.popcorn.api.response.PopcornMovieResponse;
import it.vincendep.popcorn.core.MovieRating;
import it.vincendep.popcorn.core.MovieRatingService;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PopcornHandler {

    private final MovieRatingService movieRatingService;

    public Mono<ServerResponse> queryMovies(ServerRequest request) {
        // TODO
        Pageable pageable = PageRequest.of( 1, 20, Sort.by(Sort.Order.desc("tmdb.score")));
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(movieRatingService.findBestRanked(pageable), PopcornMovieResponse.class);
    }

    public Mono<ServerResponse> getMovie(ServerRequest request) {
        // TODO
        String id = request.pathVariable("id");
        return movieRatingService.findById(id).transform(movie -> ok().body(movie, PopcornMovieResponse.class));
    }
}
