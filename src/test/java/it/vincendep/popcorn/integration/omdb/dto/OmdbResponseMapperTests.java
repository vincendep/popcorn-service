package it.vincendep.popcorn.integration.omdb.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
public class OmdbResponseMapperTests {

    @Test
    void givenNull_whenMap_thenThrows() {
        assertThatThrownBy(() -> OmdbResponseMapper.map(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenNull_whenPatch_thenThrows() {
        assertThatThrownBy(() -> OmdbResponseMapper.patch(null, null)).isInstanceOf(NullPointerException.class);
    }
}
