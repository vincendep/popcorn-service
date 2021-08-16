package it.vincendep.popcorn.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ShowRating {

    @Id
    @JsonIgnore
    private String id;
    private Tmdb tmdb;
    private Metacritic metacritic;
    private Tomatoes tomatoes;
    private Imdb imdb;

    public ShowRating(@NonNull ShowRating other) {
        this.id = other.id;
        if (other.tmdb != null) {
            this.tmdb = new Tmdb(other.tmdb);
        }
        if (other.metacritic != null) {
            this.metacritic = new Metacritic(other.metacritic);
        }
        if (other.tomatoes != null) {
            this.tomatoes = new Tomatoes(other.tomatoes);
        }
        if (other.imdb != null) {
            this.imdb = new Imdb(other.imdb);
        }
    }
}
