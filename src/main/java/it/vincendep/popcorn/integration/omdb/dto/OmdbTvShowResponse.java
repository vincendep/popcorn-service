package it.vincendep.popcorn.integration.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class OmdbTvShowResponse extends OmdbResponse {

    @JsonProperty("totalSeasons")
    private Integer totalSeasons;
}