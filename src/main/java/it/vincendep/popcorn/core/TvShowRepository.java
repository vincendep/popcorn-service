package it.vincendep.popcorn.core;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvShowRepository extends ReactiveMongoRepository<TvShow, String> {
}
