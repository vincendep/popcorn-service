package it.vincendep.popcorn.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class PopcornRouter {

    private final PopcornHandler handler;

    @Bean
    public RouterFunction<ServerResponse> popcornRoutes() {
        return route(GET("movies"), handler::queryMovies)
                .andRoute(GET("movies/{id}"), handler::getMovie)
                .andRoute(GET("tv-shows"), handler::queryTvShows)
                .andRoute(GET("tv-shows/{id}"), handler::getTvShow);
    }
}
