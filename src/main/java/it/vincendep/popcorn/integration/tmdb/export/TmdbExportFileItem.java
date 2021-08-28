package it.vincendep.popcorn.integration.tmdb.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class TmdbExportFileItem {

    @JsonProperty("id")
    private long id;
    @JsonProperty("popularity")
    private Float popularity;
}
