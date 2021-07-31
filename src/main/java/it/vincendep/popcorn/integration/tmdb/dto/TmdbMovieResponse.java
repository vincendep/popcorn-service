package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TmdbMovieResponse extends TmdbShowResponse {

    @JsonProperty("title")
    private String title;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("adult")
    private Boolean adult;
    @JsonProperty("budget")
    private Long budget;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("revenue")
    private Long revenue;
    @JsonProperty("runtime")
    private Integer runtime;
}

