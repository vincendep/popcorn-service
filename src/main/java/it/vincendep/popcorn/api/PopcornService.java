package it.vincendep.popcorn.api;

import it.vincendep.popcorn.api.response.PopcornResponse;
import it.vincendep.popcorn.core.MovieRating;
import it.vincendep.popcorn.core.MovieRatingService;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PopcornService {

    private final TmdbService tmdbService;
    private final MovieRatingService movieRatingService;

    public Flux<PopcornResponse<?>> query(Pageable pageable) {
        return movieRatingService.findAllByTmdbIsNotNull(pageable)
                .concatMap(rating -> tmdbService.getMovieDetails(rating.getTmdb().getId())
                        .onErrorResume(ex -> Mono.empty())
                        .map(movie -> new PopcornResponse<>(movie, rating)));
    }

    public Mono<PopcornResponse<?>> findByTmdbId(Long id) {
        return tmdbService.getMovieDetails(id)
                .zipWith(movieRatingService.findByTmdbId(id).defaultIfEmpty(new MovieRating()), PopcornResponse::new);
    }
}
