package it.vincendep.popcorn.faker;

import com.github.javafaker.Faker;
import it.vincendep.popcorn.core.Tmdb;

public class TmdbFaker extends BaseFaker<Tmdb>{

    public TmdbFaker() {
        this(Faker.instance());
    }

    public TmdbFaker(Faker faker) {
        super(faker);
    }

    @Override
    public Tmdb generate() {
        Tmdb tmdb = new Tmdb();
        tmdb.setId(generateUniqueId());
        tmdb.setScore((float) faker.number().randomDouble(1, 0, 10));
        tmdb.setVoteCount(faker.number().numberBetween(0, Long.MAX_VALUE));
        return tmdb;
    }

    private Long generateUniqueId() {
        return faker.number().numberBetween(0, Long.MAX_VALUE);
    }
}
