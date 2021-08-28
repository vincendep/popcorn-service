package it.vincendep.popcorn.integration.tmdb.dto;

import it.vincendep.popcorn.integration.tmdb.mapper.TmdbMapper;
import it.vincendep.popcorn.integration.tmdb.mapper.TmdbMovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class TmdbMapperTests {

    @Test
    void givenNull_whenMap_thenThrows() {
        TmdbMovieMapper mapper = TmdbMovieMapper.getInstance();
        assertThrows(NullPointerException.class, () -> mapper.map(null));
    }

    @Test
    void givenNull_whenPatch_thenThrows() {
        TmdbMovieMapper mapper = TmdbMovieMapper.getInstance();
        assertThrows(NullPointerException.class, () -> mapper.patch(null, null));
    }
}
