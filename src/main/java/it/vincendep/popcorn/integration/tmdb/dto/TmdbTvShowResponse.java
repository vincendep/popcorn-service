package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TmdbTvShowResponse extends TmdbShowResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;
    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;
    @JsonProperty("episode_run_time")
    private int[] episodeRunTime;
    @JsonProperty("first_air_date")
    private LocalDate firstAirDate;
    @JsonProperty("last_air_date")
    private LocalDate lastAirDate;
    @JsonProperty("in_production")
    private Boolean inProduction;
    @JsonProperty("type")
    private String type;
}
