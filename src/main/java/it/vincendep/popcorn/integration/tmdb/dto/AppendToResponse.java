package it.vincendep.popcorn.integration.tmdb.dto;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.google.common.base.Strings.emptyToNull;

@Getter(AccessLevel.PRIVATE)
@RequiredArgsConstructor
public enum AppendToResponse {
    WATCH_PROVIDERS("watch/providers"),
    VIDEOS("videos");

    private final String queryParam;

    public static String queryString(AppendToResponse ...appendToResponses ) {
    	return emptyToNull(Arrays
                .stream(appendToResponses)
                .map(AppendToResponse::getQueryParam)
                .collect(Collectors.joining(",")));
    }
}
