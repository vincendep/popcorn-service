package it.vincendep.popcorn.integration.omdb.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "Type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OmdbMovieResponse.class, name = "movie"),
        @JsonSubTypes.Type(value = OmdbTvShowResponse.class, name = "series"),
        @JsonSubTypes.Type(value = OmdbTvEpisodeResponse.class, name = "episode")
})
public abstract class OmdbResponse {

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Year")
    private String year;
    @JsonProperty("Rated")
    private String rated;
    @JsonProperty("Released")
    private String released;
    @JsonProperty("Runtime")
    private String runtime;
    @JsonProperty("Genre")
    private String[] genre;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Writer")
    private String writer;
    @JsonProperty("Actors")
    private String[] actors;
    @JsonProperty("Plot")
    private String plot;
    @JsonProperty("Language")
    private String language;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Awards")
    private String awards;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("Ratings")
    private Map<String, String> ratings;
    @JsonProperty("Metascore")
    private Integer metascore;
    private Float imdbRating;
    private Long imdbVotes;
    @JsonProperty("imdbID")
    private String imdbId;
    @JsonProperty("Type")
    private String type;

    @JsonIgnore
    private Boolean response;
    @JsonIgnore
    private String error;

    @JsonSetter("Response")
    public void setResponse(Boolean response) {
        this.response = response;
    }

    @JsonSetter("Error")
    public void setError(String error) {
        this.error = error;
    }

    @JsonSetter("Actors")
    public void setActors(String actors) {
        if (actors != null) {
            this.actors = actors.split(", ");
        }
    }

    @JsonSetter("Ratings")
    public void setRatings(List<Map<String, String>> ratings) {
        if (ratings != null) {
            this.ratings = ratings.stream().collect(toMap(map -> map.get("Source"), map -> map.get("Value")));
        }
    }

    @JsonSetter("Genre")
    public void setGenre(String genre) {
        if (genre != null) {
            this.genre = genre.split(", ");
        }
    }

    @JsonSetter("Metascore")
    public void setMetascore(String metascore) {
        try {
            this.metascore = Integer.valueOf(metascore);
        } catch (NumberFormatException ignored) {}
    }

    @JsonSetter("imdbVotes")
    public void setImdbVotes(String imdbVotes) {
        try {
            this.imdbVotes = Long.valueOf(imdbVotes.replaceAll(",", ""));
        } catch (NumberFormatException | NullPointerException ignored) {}
    }

    @JsonSetter("imdbRating")
    public void setImdbRating(String rating) {
        try {
            this.imdbRating = Float.valueOf(rating);
        } catch (NumberFormatException ignored) {}
    }
}
