package it.vincendep.popcorn.integration.tmdb.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class TmdbRouter {

    private final TmdbHandler handler;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(GET("tmdb/movie/{id}"), handler::getMovieDetails);
    }
}
