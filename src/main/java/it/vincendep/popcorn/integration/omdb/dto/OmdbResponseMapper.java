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

    public static MovieRating map(OmdbMovieResponse response, MovieRating base) {
        return patch(response, new MovieRating(base));
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
                switch (rating.getKey()) {
                    case "Rotten Tomatoes":
                        Integer tomatometer = Integer.valueOf(rating.getValue().replace('%', '\0'));
                        Tomatoes tomatoes = movieRating.getTomatoes();
                        if (tomatoes == null) {
                            tomatoes = new Tomatoes();
                            movieRating.setTomatoes(tomatoes);
                        }
                        tomatoes.setTomatometer(tomatometer);
                        break;
                }
            } catch (NumberFormatException ignored) {}
        }
        return movieRating;
    }
}
