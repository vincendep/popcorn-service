package it.vincendep.popcorn.integration.omdb.mapper;

import it.vincendep.popcorn.core.TvShow;

public class OmdbTvShowMapper extends OmdbMapper<TvShow> {

    private static final OmdbTvShowMapper INSTANCE = new OmdbTvShowMapper();

    private OmdbTvShowMapper() {
        super(TvShow.class);
    }

    public static OmdbMapper<TvShow> getInstance() {
        return INSTANCE;
    }
}
