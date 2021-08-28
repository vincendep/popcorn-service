package it.vincendep.popcorn.integration.tmdb.mapper;

import it.vincendep.popcorn.core.TvShow;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbTvShowResponse;

public class TmdbTvShowMapper extends TmdbMapper<TmdbTvShowResponse, TvShow> {

    private final static TmdbTvShowMapper INSTANCE = new TmdbTvShowMapper();

    public static TmdbTvShowMapper getInstance() {
        return INSTANCE;
    }

    private TmdbTvShowMapper() {
        super(TvShow.class);
    }
}
