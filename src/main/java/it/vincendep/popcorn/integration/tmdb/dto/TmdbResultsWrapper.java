package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TmdbResultsWrapper<T> {

    @JsonProperty("results")
    private T results;
}
