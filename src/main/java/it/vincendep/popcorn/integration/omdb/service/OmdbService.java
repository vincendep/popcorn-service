package it.vincendep.popcorn.integration.omdb.service;

import it.vincendep.popcorn.integration.omdb.dto.OmdbResponse;
import it.vincendep.popcorn.integration.omdb.exception.OmdbException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OmdbService {

    private final WebClient omdbClient;

    public OmdbService(@Qualifier("omdbClient") WebClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    public Mono<OmdbResponse> getById(String imdbId) {
        return omdbClient.get()
                .uri(builder -> builder.queryParam("i", imdbId).build())
                .retrieve()
                .bodyToMono(OmdbResponse.class)
                .handle((res, sink) -> {
                    if (res.getResponse()) {
                        sink.next(res);
                    } else {
                        sink.error(new OmdbException(res.getError()));
                    }
                });
    }
}
