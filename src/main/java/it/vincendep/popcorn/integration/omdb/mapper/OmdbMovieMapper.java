package it.vincendep.popcorn.integration.omdb.mapper;

import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.integration.omdb.dto.OmdbResponse;

public class OmdbMovieMapper extends OmdbMapper<Movie> {

    private static final OmdbMovieMapper INSTANCE = new OmdbMovieMapper();

    private OmdbMovieMapper() {
        super(Movie.class);
    }

    public static OmdbMapper<Movie> getInstance() {
        return INSTANCE;
    }
}
