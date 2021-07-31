package it.vincendep.popcorn.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ShowRating {

    @Id
    @JsonIgnore
    private Long id;
    private Tmdb tmdb;
    private Metacritic metacritic;
    private Tomatoes tomatoes;
    private Imdb imdb;
}
