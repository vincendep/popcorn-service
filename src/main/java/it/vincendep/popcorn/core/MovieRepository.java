package it.vincendep.popcorn.core;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
interface MovieRepository extends ReactiveMongoRepository<Movie, String> {

    @Query(value = "{ id: { $exists: true }}") // https://stackoverflow.com/questions/46384618/how-apply-pagination-in-reactive-spring-data
    Flux<Movie> findAll(Pageable pageable);
    Flux<Movie> findAllByTmdbIsNotNull(Pageable pageable);
    Mono<Movie> findByTmdb_Id(Long tmdbId);
    Mono<Movie> findByImdb_Id(String imdbId);
}
