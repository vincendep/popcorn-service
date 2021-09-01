package it.vincendep.popcorn.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Flux<Movie> findAllByTmdbIsNotNull(Pageable pageable) {
        return movieRepository.findAllByTmdbIsNotNull(pageable);
    }

    public Mono<Movie> findByTmdbId(Long id) {
        return movieRepository.findByTmdb_Id(id);
    }
}
