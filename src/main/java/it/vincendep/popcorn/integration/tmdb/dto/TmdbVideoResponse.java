package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TmdbVideoResponse {

    @JsonProperty("id")
    private Long tmdbId;
    @JsonProperty("iso_639_1")
    private String iso639_1;
    @JsonProperty("iso_3166_1")
    private String iso3166_1;
    private String key;
    private String name;
    private String site;
    private Integer size;
    private String type;
}
