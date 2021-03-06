package it.vincendep.popcorn.integration.tmdb.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TmdbMovieExportFileItem extends TmdbExportFileItem {

    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("video")
    private boolean video;
    @JsonProperty("adult")
    private boolean adult;
}
