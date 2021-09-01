package it.vincendep.popcorn.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TvShowService {

    private final TvShowRepository repository;

    public Flux<TvShow> findAllByTmdbIsNotNull(Pageable pageable) {
        return repository.findAllByTmdbIsNotNull(pageable);
    }

    public Mono<TvShow> findByTmdbId(Long id) {
        return repository.findByTmdb_Id(id);
    }
}
