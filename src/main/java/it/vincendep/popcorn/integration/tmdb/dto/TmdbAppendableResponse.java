package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Data
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TmdbAppendableResponse<T>  {

    @JsonUnwrapped
    private T response;
    private Map<String, TmdbMovieWatchProvidersResponse> watchProviders;
    private List<TmdbVideoResponse> videos;

    @JsonSetter("videos")
    public void deserializeVideos(TmdbResponseWrapper<List<TmdbVideoResponse>> videosWrapper) {
        videos = videosWrapper.getResults();
    }

    @JsonSetter("watch/providers")
    public void deserializeWatchProviders(TmdbResponseWrapper<Map<String, TmdbMovieWatchProvidersResponse>> watchProviderWrapper) {
        this.watchProviders = watchProviderWrapper.getResults();
    }
}
