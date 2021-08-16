package it.vincendep.popcorn.integration.tmdb.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class TmdbExportFileItem {

    @JsonProperty("id")
    private long id;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("popularity")
    private Float popularity;
    @JsonProperty("video")
    private boolean video;
    @JsonProperty("adult")
    private boolean adult;
}
