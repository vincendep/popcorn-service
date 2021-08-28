package it.vincendep.popcorn.integration.tmdb.export;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TmdbTvShowExportFileItem extends TmdbExportFileItem {

    @JsonProperty("original_name")
    private String originalName;
}
