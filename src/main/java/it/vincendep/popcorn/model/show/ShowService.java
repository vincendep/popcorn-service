package it.vincendep.popcorn.model.show;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final MovieService movieService;
    private final TvShowService tvShowService;

    public Flux<Show> findAll() {
        return Flux.merge(movieService.findAll(), tvShowService.findAll());
    }
}
