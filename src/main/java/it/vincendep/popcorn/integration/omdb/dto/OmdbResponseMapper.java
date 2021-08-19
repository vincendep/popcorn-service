package it.vincendep.popcorn.integration.omdb.dto;

import it.vincendep.popcorn.core.Imdb;
import it.vincendep.popcorn.core.Metacritic;
import it.vincendep.popcorn.core.MovieRating;
import it.vincendep.popcorn.core.Tomatoes;

import java.util.Map;

public class OmdbResponseMapper {

    public static MovieRating map(OmdbMovieResponse response) {
        return patch(response, new MovieRating());
    }

    public static MovieRating patch(OmdbMovieResponse response, MovieRating movieRating) {
        if (response.getImdbId() != null) {
            Imdb imdb = movieRating.getImdb();
            if (imdb == null) {
                imdb = new Imdb();
                movieRating.setImdb(imdb);
            }
            imdb.setId(response.getImdbId());
            imdb.setWeightedAverage(response.getImdbRating());
            imdb.setScores(response.getImdbVotes());
        }
        if (response.getMetascore() != null) {
            Metacritic metacritic = movieRating.getMetacritic();
            if (metacritic == null) {
                metacritic = new Metacritic();
                movieRating.setMetacritic(metacritic);
            }
            metacritic.setMetascore(response.getMetascore());
        }
        for (Map.Entry<String, String> rating: response.getRatings().entrySet()) {
            try {
                if ("Rotten Tomatoes".equals(rating.getKey())) {
                    String value = rating.getValue();
                    Integer tomatometer = Integer.valueOf(value.substring(0, value.length() - 1));
                    Tomatoes tomatoes = movieRating.getTomatoes();
                    if (tomatoes == null) {
                        tomatoes = new Tomatoes();
                        movieRating.setTomatoes(tomatoes);
                    }
                    tomatoes.setTomatometer(tomatometer);
                }
            } catch (NumberFormatException ignored) {}
        }
        return movieRating;
    }
}
