package it.vincendep.popcorn.integration.tmdb.dto;

import lombok.Data;

@Data
public class TmdbResponseWrapper<T> {

    private T results;
}
