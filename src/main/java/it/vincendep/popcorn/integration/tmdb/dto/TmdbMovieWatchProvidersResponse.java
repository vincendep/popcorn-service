package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TmdbMovieWatchProvidersResponse {

    @JsonProperty("buy")
    private List<TmdbWatchProviderResponse> buy;
    @JsonProperty("flatrate")
    private List<TmdbWatchProviderResponse> flatRate;
    @JsonProperty("rent")
    private List<TmdbWatchProviderResponse> rent;
}
