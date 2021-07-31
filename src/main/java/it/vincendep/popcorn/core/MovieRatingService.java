package it.vincendep.popcorn.core;

import it.vincendep.popcorn.api.response.PopcornMovieResponse;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class MovieRatingService {

    private final TmdbService tmdbService;
    private final MovieRatingRepository movieRatingRepository;

    public Flux<PopcornMovieResponse<?>> findBestRanked(Pageable pageable) {
        return movieRatingRepository.findAll(pageable).flatMap(rating ->
                        tmdbService.getMovieDetails(rating.getId()).map(movie ->
                                new PopcornMovieResponse<>(movie, rating)));
    }

    public Mono<PopcornMovieResponse<?>> findById(String id) {
        return tmdbService.getMovieDetails(Long.valueOf(id))
                .zipWith(movieRatingRepository.findById(Long.valueOf(id)).defaultIfEmpty(new MovieRating()), PopcornMovieResponse::new);

    }
}
