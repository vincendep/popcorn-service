package it.vincendep.popcorn.integration.omdb.dto;

import it.vincendep.popcorn.core.Imdb;
import it.vincendep.popcorn.core.Metacritic;
import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.core.Tomatoes;

import java.util.Map;

public class OmdbResponseMapper {

    public static Movie map(OmdbMovieResponse response) {
        return patch(response, new Movie());
    }

    public static Movie patch(OmdbMovieResponse response, Movie movie) {
        if (response.getImdbId() != null) {
            Imdb imdb = movie.getImdb();
            if (imdb == null) {
                imdb = new Imdb();
                movie.setImdb(imdb);
            }
            imdb.setId(response.getImdbId());
            imdb.setWeightedAverage(response.getImdbRating());
            imdb.setScores(response.getImdbVotes());
        }
        if (response.getMetascore() != null) {
            Metacritic metacritic = movie.getMetacritic();
            if (metacritic == null) {
                metacritic = new Metacritic();
                movie.setMetacritic(metacritic);
            }
            metacritic.setMetascore(response.getMetascore());
        }
        for (Map.Entry<String, String> rating: response.getRatings().entrySet()) {
            try {
                if ("Rotten Tomatoes".equals(rating.getKey())) {
                    String value = rating.getValue();
                    Integer tomatometer = Integer.valueOf(value.substring(0, value.length() - 1));
                    Tomatoes tomatoes = movie.getTomatoes();
                    if (tomatoes == null) {
                        tomatoes = new Tomatoes();
                        movie.setTomatoes(tomatoes);
                    }
                    tomatoes.setTomatometer(tomatometer);
                }
            } catch (NumberFormatException ignored) {}
        }
        return movie;
    }
}
