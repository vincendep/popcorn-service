package it.vincendep.popcorn.faker;

import com.github.javafaker.Faker;
import it.vincendep.popcorn.core.Metacritic;

public class MetacriticFaker extends BaseFaker<Metacritic> {

    public MetacriticFaker(Faker faker) {
        super(faker);
    }

    @Override
    public Metacritic generate() {
        Metacritic metacritic = new Metacritic();
        metacritic.setMetascore(faker.number().numberBetween(0, 100));
        metacritic.setCriticReviews(faker.number().numberBetween(0, Integer.MAX_VALUE));
        metacritic.setUserScore((float) faker.number().randomDouble(1, 0, 10));
        metacritic.setUserReviews(faker.number().numberBetween(0L, Long.MAX_VALUE));
        return metacritic;
    }
}
