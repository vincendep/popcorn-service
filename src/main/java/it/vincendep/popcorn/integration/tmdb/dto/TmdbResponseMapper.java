package it.vincendep.popcorn.integration.tmdb.dto;

import it.vincendep.popcorn.core.Imdb;
import it.vincendep.popcorn.core.MovieRating;
import it.vincendep.popcorn.core.Tmdb;

public class TmdbResponseMapper {

    public static MovieRating map(TmdbMovieResponse response) {
        return patch(response, new MovieRating());
    }

    public static MovieRating patch(TmdbMovieResponse response, MovieRating movieRating) {
        Tmdb tmdb = movieRating.getTmdb();
        if (tmdb == null) {
            tmdb = new Tmdb();
            movieRating.setTmdb(tmdb);
        }
        tmdb.setId(response.getTmdbId());
        tmdb.setScore(response.getVoteAverage());
        tmdb.setVoteCount(response.getVoteCount());

        Imdb imdb = movieRating.getImdb();
        if (imdb == null) {
            imdb = new Imdb();
            movieRating.setImdb(imdb);
        }
        imdb.setId(response.getImdbId());
        return movieRating;
    }
}
