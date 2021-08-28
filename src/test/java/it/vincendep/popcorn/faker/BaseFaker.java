package it.vincendep.popcorn.faker;

import com.github.javafaker.Faker;

public abstract class BaseFaker<T> {

    protected final Faker faker;

    protected BaseFaker(Faker faker) {
        this.faker = faker;
    }

    public abstract T generate();
}
