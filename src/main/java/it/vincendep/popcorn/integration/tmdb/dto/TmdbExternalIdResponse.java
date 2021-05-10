package it.vincendep.popcorn.integration.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TmdbExternalIdResponse {

	@JsonProperty("id")
	private Long tmdbId;
	@JsonProperty("imdb_id")
	private String imdbId;
	@JsonProperty("facebook_id")
	private String facebookId;
	@JsonProperty("instagram_id")
	private String instagramId;
	@JsonProperty("twitter_id")
	private String twitterId;
}
