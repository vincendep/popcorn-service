package it.vincendep.popcorn.faker;

import com.github.javafaker.Faker;
import it.vincendep.popcorn.core.Movie;
import org.bson.types.ObjectId;

public class MovieFaker extends BaseFaker<Movie> {

    private final ImdbFaker imdbFaker;
    private final MetacriticFaker metacriticFaker;
    private final TomatoesFaker tomatoesFaker;
    private final TmdbFaker tmdbFaker;

    public MovieFaker() {
        this(Faker.instance());
    }

    public MovieFaker(Faker faker) {
        super(faker);
        imdbFaker = new ImdbFaker(faker);
        metacriticFaker = new MetacriticFaker(faker);
        tomatoesFaker = new TomatoesFaker(faker);
        tmdbFaker = new TmdbFaker(faker);
    }

    public Movie generate() {
        Movie movie = new Movie();
        movie.setId(generateUniqueId());
        movie.setImdb(imdbFaker.generate());
        movie.setMetacritic(metacriticFaker.generate());
        movie.setTomatoes(tomatoesFaker.generate());
        movie.setTmdb(tmdbFaker.generate());
        return movie;
    }

    private String generateUniqueId() {
        return ObjectId.get().toHexString();
    }
}
