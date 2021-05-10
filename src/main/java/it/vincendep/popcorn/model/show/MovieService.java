package it.vincendep.popcorn.model.show;

import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Flux<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Flux<Movie> saveAll(Publisher<Movie> publisher) {
        return movieRepository.saveAll(publisher);
    }

    public Flux<Movie> saveAll(Iterable<Movie> movies) {
        return movieRepository.saveAll(movies);
    }
}
