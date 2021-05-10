package it.vincendep.popcorn.integration.tmdb.mapper;

import com.google.common.base.Strings;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import it.vincendep.popcorn.model.rating.Tmdb;
import it.vincendep.popcorn.model.show.Movie;

import static com.google.common.base.Strings.emptyToNull;

public class MovieMapper {

    public static Movie map(TmdbMovieResponse res) {
        Movie movie = new Movie();
        movie.setTmdbId(res.getTmdbId());
        movie.setImdbId(emptyToNull(res.getImdbId()));
        if (res.getVoteAverage() != null || res.getVoteCount() != null) {
            Tmdb tmdb = new Tmdb();
            tmdb.setVoteCount(res.getVoteCount());
            tmdb.setScore(res.getVoteAverage());
            movie.setTmdb(tmdb);
        }
        return movie;
    }
}
