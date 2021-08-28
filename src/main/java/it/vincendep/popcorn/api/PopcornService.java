package it.vincendep.popcorn.api;

import it.vincendep.popcorn.api.response.PopcornResponse;
import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.core.MovieService;
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
    private final MovieService movieService;

    public Flux<PopcornResponse<?>> query(Pageable pageable) {
        return movieService.findAllByTmdbIsNotNull(pageable)
                .concatMap(rating -> tmdbService.getMovieDetails(rating.getTmdb().getId())
                        .onErrorResume(ex -> Mono.empty())
                        .map(movie -> new PopcornResponse<>(movie, rating)));
    }

    public Mono<PopcornResponse<?>> findByTmdbId(Long id) {
        return tmdbService.getMovieDetails(id)
                .zipWith(movieService.findByTmdbId(id).defaultIfEmpty(new Movie()), PopcornResponse::new);
    }
}
