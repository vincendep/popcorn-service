package it.vincendep.popcorn.integration.tmdb.mapper;

import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;

public class TmdbMovieMapper extends TmdbMapper<TmdbMovieResponse, Movie> {

    private final static TmdbMovieMapper INSTANCE = new TmdbMovieMapper();

    private TmdbMovieMapper() {
        super(Movie.class);
    }

    public static TmdbMovieMapper getInstance() {
        return INSTANCE;
    }
}
