package it.vincendep.popcorn.api;

import it.vincendep.popcorn.api.response.PopcornResponse;
import it.vincendep.popcorn.common.QueryParameterExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PopcornHandler {

    private final PopcornService popcornService;
    private final QueryParameterExtractor extractor;

    public @NonNull Mono<ServerResponse> queryMovies(ServerRequest request) {
        var pageable = getPageable(request);
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(popcornService.queryMovies(pageable), PopcornResponse.class);
    }

    public @NonNull Mono<ServerResponse> getMovie(ServerRequest request) {
        try {
            Long tmdbId = Long.valueOf(request.pathVariable("id"));
            return popcornService.findMovieByTmdbId(tmdbId)
                    .flatMap(movie -> ok().bodyValue(movie))
                    .switchIfEmpty(ServerResponse.notFound().build());
        } catch (NumberFormatException ex) {
            return ServerResponse.badRequest().build();
        }
    }

    public @NonNull Mono<ServerResponse> queryTvShows(ServerRequest request) {
        var pageable = getPageable(request);
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(popcornService.queryTvShows(pageable), PopcornResponse.class);
    }

    public @NonNull Mono<ServerResponse> getTvShow(ServerRequest request) {
        try {
            Long tmdbId = Long.valueOf(request.pathVariable("id"));
            return popcornService.findTvShowByTmdbId(tmdbId)
                    .flatMap(tvShow -> ok().bodyValue(tvShow))
                    .switchIfEmpty(ServerResponse.notFound().build());
        } catch (NumberFormatException ex) {
            return ServerResponse.badRequest().build();
        }
    }

    private Pageable getPageable(ServerRequest request) {
        return extractor.getPageable(request.exchange());
    }
}
