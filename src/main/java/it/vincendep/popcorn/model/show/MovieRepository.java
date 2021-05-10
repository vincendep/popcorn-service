package it.vincendep.popcorn.model.show;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {
	
	Mono<Movie> findByTmdbId(Long tmdbId);
	Mono<Movie> findByImdbId(String imdbId);
	Flux<Movie> findAllByTmdbId(Iterable<Long> tmdbIds);
}
