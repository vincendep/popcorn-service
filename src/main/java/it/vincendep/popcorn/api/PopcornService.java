package it.vincendep.popcorn.api;

import it.vincendep.popcorn.api.response.PopcornResponse;
import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.core.MovieService;
import it.vincendep.popcorn.core.TvShow;
import it.vincendep.popcorn.core.TvShowService;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PopcornService {

    private final TmdbService tmdbService;
    private final MovieService movieService;
    private final TvShowService tvShowService;

    public Flux<PopcornResponse<?, Movie>> queryMovies(Pageable pageable) {
        return movieService.findAllByTmdbIsNotNull(pageable)
                .concatMap(data -> tmdbService.getMovieDetails(data.getTmdb().getId())
                        .onErrorResume(ex -> Mono.empty())
                        .map(movie -> new PopcornResponse<>(movie, data)));
    }

    public Flux<PopcornResponse<?, TvShow>> queryTvShows(Pageable pageable) {
        return tvShowService.findAllByTmdbIsNotNull(pageable)
                .concatMap(data -> tmdbService.getTvShowDetails(data.getTmdb().getId())
                        .onErrorResume(ex -> Mono.empty())
                        .map(tvShow -> new PopcornResponse<>(tvShow, data)));
    }

    public Mono<PopcornResponse<?, Movie>> findMovieByTmdbId(Long id) {
        return tmdbService.getMovieDetails(id)
                .onErrorResume(ex -> ex instanceof WebClientResponseException.NotFound, ex  -> Mono.empty())
                .zipWith(movieService.findByTmdbId(id).defaultIfEmpty(new Movie()), PopcornResponse::new);
    }

    public Mono<PopcornResponse<?, TvShow>> findTvShowByTmdbId(Long id) {
        return tmdbService.getTvShowDetails(id)
                .onErrorResume(ex -> ex instanceof WebClientResponseException.NotFound, ex  -> Mono.empty())
                .zipWith(tvShowService.findByTmdbId(id).defaultIfEmpty(new TvShow()), PopcornResponse::new);
    }
}
