package it.vincendep.popcorn.integration.tmdb.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor
public enum AppendToResponse {
    EXTERNAL_IDS("external_ids"),
    WATCH_PROVIDERS("watch/providers"),
    VIDEOS("videos");

    private final String queryParam;

    public static String queryString(AppendToResponse ...appendToResponses ) {
    	return Arrays.stream(appendToResponses)
                .map(AppendToResponse::getQueryParam)
                .collect(Collectors.joining(","));
    }
}
