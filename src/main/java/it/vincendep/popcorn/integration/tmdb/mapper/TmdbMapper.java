package it.vincendep.popcorn.integration.tmdb.mapper;

import it.vincendep.popcorn.common.Mapper;
import it.vincendep.popcorn.core.*;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbShowResponse;

public abstract class TmdbMapper<I extends TmdbShowResponse, O extends Show> extends Mapper<I, O> {

    protected TmdbMapper(Class<O> clazz) {
        super(clazz);
    }

    @Override
    public O patch(I response, O show) {
        super.patch(response, show);

        Tmdb tmdb = show.getTmdb();
        if (tmdb == null) {
            tmdb = new Tmdb();
            show.setTmdb(tmdb);
        }
        tmdb.setId(response.getTmdbId());
        tmdb.setScore(response.getVoteAverage());
        tmdb.setVoteCount(response.getVoteCount());

        Imdb imdb = show.getImdb();
        if (imdb == null) {
            imdb = new Imdb();
            show.setImdb(imdb);
        }
        imdb.setId(response.getImdbId());
        return show;
    }
}
