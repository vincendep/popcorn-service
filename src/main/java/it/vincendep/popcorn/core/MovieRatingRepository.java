package it.vincendep.popcorn.core;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
interface MovieRatingRepository extends ReactiveMongoRepository<MovieRating, String> {

    @Query(value = "{ id: { $exists: true }}") // https://stackoverflow.com/questions/46384618/how-apply-pagination-in-reactive-spring-data
    Flux<MovieRating> findAll(Pageable pageable);
    Mono<MovieRating> findByTmdb_Id(Long tmdbId);
    Mono<MovieRating> findByImdb_Id(String imdbId);
}
