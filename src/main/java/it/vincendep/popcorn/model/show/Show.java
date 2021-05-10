package it.vincendep.popcorn.model.show;

import it.vincendep.popcorn.model.rating.Imdb;
import it.vincendep.popcorn.model.rating.Metacritic;
import it.vincendep.popcorn.model.rating.Tmdb;
import it.vincendep.popcorn.model.rating.Tomatoes;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class Show {

    @Id
    private Long tmdbId;
    // scores
    private Tmdb tmdb;
    private Metacritic metacritic;
    private Tomatoes tomatoes;
    private Imdb imdb;
}
