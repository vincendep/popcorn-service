package it.vincendep.popcorn.model.show;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class TvShowService {

    private final TvShowRepository tvShowRepository;

    public Flux<TvShow> findAll() {
        return tvShowRepository.findAll();
    }
}
