package it.vincendep.popcorn.integration.job.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@RequiredArgsConstructor
public class JobRouter {

    private final JobHandler handler;

    @Bean
    public RouterFunction<ServerResponse> jobRoutes() {
        return route(POST("jobs/{jobName}/start"), handler::runJob)
                .andRoute(POST("jobs/{jobName}/stop"), handler::stopJob);
    }
}
