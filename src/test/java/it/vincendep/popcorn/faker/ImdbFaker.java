package it.vincendep.popcorn.faker;

import com.github.javafaker.Faker;
import it.vincendep.popcorn.core.Imdb;

public class ImdbFaker extends BaseFaker<Imdb> {

    public ImdbFaker() {
        this(Faker.instance());
    }

    public ImdbFaker(Faker faker) {
        super(faker);
    }

    @Override
    public Imdb generate() {
        Imdb imdb = new Imdb();
        imdb.setId(generateUniqueId());
        imdb.setWeightedAverage((float) faker.number().randomDouble(1, 0, 10));
        imdb.setScores(faker.number().numberBetween(0L, Long.MAX_VALUE));
        return imdb;
    }

    private String generateUniqueId() {
        return faker.numerify("tt???????");
    }
}
