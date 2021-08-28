package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TmdbAppendableResponse<T>  {

    @JsonUnwrapped
    private T response;
    @JsonProperty("watch/providers")
    private TmdbResultsWrapper<Map<String, TmdbMovieWatchProvidersResponse>> watchProviders;
    @JsonProperty("videos")
    private TmdbResultsWrapper<List<TmdbVideoResponse>> videos;
}
