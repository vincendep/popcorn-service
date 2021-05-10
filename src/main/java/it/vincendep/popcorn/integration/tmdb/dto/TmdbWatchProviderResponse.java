package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TmdbWatchProviderResponse {

    @JsonProperty("provider_id")
    private Integer id;
    @JsonProperty("logo_path")
    private String logo;
    @JsonProperty("provider_name")
    private String name;
    @JsonProperty("display_priority")
    private Integer displayPriority;
}
