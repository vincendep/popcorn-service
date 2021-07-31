package it.vincendep.popcorn.core;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
interface MovieRatingRepository extends ReactiveMongoRepository<MovieRating, Long> {

    Mono<MovieRating> findByTmdb_Id(Long tmdbId);
    Mono<MovieRating> findByImdb_Id(String imdbId);
}
