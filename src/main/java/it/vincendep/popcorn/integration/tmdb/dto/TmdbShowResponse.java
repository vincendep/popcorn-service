package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class TmdbShowResponse {

    @JsonProperty("id")
    private Long tmdbId;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("vote_average")
    private Float voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
}
