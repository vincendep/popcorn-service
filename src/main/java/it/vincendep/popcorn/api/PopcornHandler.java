package it.vincendep.popcorn.api;

import it.vincendep.popcorn.api.response.PopcornResponse;
import it.vincendep.popcorn.common.QueryParameterExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class PopcornHandler {

    private final QueryParameterExtractor parameterExtractor;
    private final PopcornService popcornService;

    public @NonNull Mono<ServerResponse> queryMovies(ServerRequest request) {
        var pageable = parameterExtractor.getPageable(request.exchange());
        return ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(popcornService.query(pageable), PopcornResponse.class);
    }

    public @NonNull Mono<ServerResponse> getMovie(ServerRequest request) {
        return Mono.just(request.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(popcornService::findByTmdbId)
                .transform(res -> ok().body(res, PopcornResponse.class));
    }
}
