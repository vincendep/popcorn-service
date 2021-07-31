package it.vincendep.popcorn.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class MovieRatingService {

    private final MovieRatingRepository movieRepository;

    public Mono<MovieRating> findById(String id) {
        return movieRepository.findByTmdb_Id(Long.valueOf(id));
    }
}
