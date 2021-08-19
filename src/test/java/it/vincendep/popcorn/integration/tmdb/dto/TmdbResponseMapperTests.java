package it.vincendep.popcorn.integration.tmdb.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class TmdbResponseMapperTests {

    @Test
    void givenNull_whenMap_thenThrows() {
        assertThrows(NullPointerException.class, () -> TmdbResponseMapper.map(null));
    }

    @Test
    void givenNull_whenPatch_thenThrows() {
        assertThrows(NullPointerException.class, () -> TmdbResponseMapper.patch(null, null));
    }
}
