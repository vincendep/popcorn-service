package it.vincendep.popcorn.integration.omdb.mapper;

import it.vincendep.popcorn.common.Mapper;
import it.vincendep.popcorn.core.Imdb;
import it.vincendep.popcorn.core.Metacritic;
import it.vincendep.popcorn.core.Show;
import it.vincendep.popcorn.core.Tomatoes;
import it.vincendep.popcorn.integration.omdb.dto.OmdbResponse;
import java.util.Map;

public abstract class OmdbMapper<O extends Show> extends Mapper<OmdbResponse, O> {

    protected OmdbMapper(Class<O> clazz) {
        super(clazz);
    }

    @Override
    public O patch(OmdbResponse response, O show) {
        super.patch(response, show);
        if (response.getImdbId() != null) {
            Imdb imdb = show.getImdb();
            if (imdb == null) {
                imdb = new Imdb();
                show.setImdb(imdb);
            }
            imdb.setId(response.getImdbId());
            imdb.setWeightedAverage(response.getImdbRating());
            imdb.setScores(response.getImdbVotes());
        }
        if (response.getMetascore() != null) {
            Metacritic metacritic = show.getMetacritic();
            if (metacritic == null) {
                metacritic = new Metacritic();
                show.setMetacritic(metacritic);
            }
            metacritic.setMetascore(response.getMetascore());
        }
        for (Map.Entry<String, String> rating: response.getRatings().entrySet()) {
            try {
                if ("Rotten Tomatoes".equals(rating.getKey())) {
                    String value = rating.getValue();
                    Integer tomatometer = Integer.valueOf(value.substring(0, value.length() - 1));
                    Tomatoes tomatoes = show.getTomatoes();
                    if (tomatoes == null) {
                        tomatoes = new Tomatoes();
                        show.setTomatoes(tomatoes);
                    }
                    tomatoes.setTomatometer(tomatometer);
                }
            } catch (NumberFormatException ignored) {}
        }
        return show;
    }
}
