package it.vincendep.popcorn.faker;

import com.github.javafaker.Faker;
import it.vincendep.popcorn.core.Tomatoes;

public class TomatoesFaker extends BaseFaker<Tomatoes> {

    public TomatoesFaker() {
        this(Faker.instance());
    }

    public TomatoesFaker(Faker faker) {
        super(faker);
    }

    @Override
    public Tomatoes generate() {
        Tomatoes tomatoes = new Tomatoes();
        tomatoes.setAudienceScore(faker.number().numberBetween(0, 100));
        tomatoes.setCertifiedFresh(faker.bool().bool());
        tomatoes.setPositiveReviews(faker.number().numberBetween(0, Long.MAX_VALUE / 2));
        tomatoes.setNegativeReviews(faker.number().numberBetween(0, Long.MAX_VALUE / 2));
        tomatoes.setRatings(tomatoes.getNegativeReviews() + tomatoes.getPositiveReviews());
        tomatoes.setTomatometer((int) (tomatoes.getPositiveReviews() / tomatoes.getRatings() * 100));
        return tomatoes;
    }
}
