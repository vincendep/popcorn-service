package it.vincendep.popcorn.integration.imdb;

import it.vincendep.popcorn.model.rating.Imdb;
import it.vincendep.popcorn.model.rating.Metacritic;
import lombok.Data;

@Data
public class RatingData {

    private String imdbId;
    private Imdb imdb;
    private Metacritic metacritic;
}
