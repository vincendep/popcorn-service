package it.vincendep.popcorn.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieRatingService {

    private final MovieRatingRepository movieRatingRepository;

    public Flux<MovieRating> findAll(Pageable pageable) {
        return movieRatingRepository.findAll(pageable);
    }

    public Mono<MovieRating> findByTmdbId(Long id) {
        return movieRatingRepository.findByTmdb_Id(id);
    }
}
