package it.vincendep.popcorn.integration.omdb.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class OmdbRouter {

    private final OmdbHandler handler;

    @Bean
    public RouterFunction<ServerResponse> omdbRoutes() {
        return route(GET("omdb/movie/{imdbId}"), handler::getMovieDetails);
    }
}
