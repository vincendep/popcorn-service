package it.vincendep.popcorn.common;

import it.vincendep.popcorn.integration.omdb.exception.MapperException;

import java.lang.reflect.InvocationTargetException;

public abstract class Mapper<I,O> {

    private final Class<O> clazz;

    protected Mapper(Class<O> clazz) {
        this.clazz = clazz;
    }

    public final O map(I i) {
        try {
            return patch(i, clazz.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new MapperException("Cannot instantiate class: " + clazz.getName(), ex);
        }
    }

    public O patch(I i, O o) { return o; }
}
