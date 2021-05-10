package it.vincendep.popcorn.model.show;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TvShowRepository extends ReactiveMongoRepository<TvShow, Long> {
}
