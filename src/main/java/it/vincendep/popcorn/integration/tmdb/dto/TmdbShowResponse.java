package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class TmdbShowResponse {

    @JsonProperty("id")
    private Long tmdbId;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("vote_average")
    private Float voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
    @JsonProperty("popularity")
    private Float popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("status")
    private String status;
    @JsonProperty("tagline")
    private String tagline;
}
