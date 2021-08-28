package it.vincendep.popcorn.integration.omdb.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.integration.omdb.dto.OmdbResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
public class OmdbMovieMapperTests {

    @Test
    void givenMovieResponse_whenMap_thenIsCorrect() {
        OmdbResponse response = fakeResponse();
        Movie movie = OmdbMovieMapper.getInstance().map(response);
        if (movie.getImdb() != null) {
            assertThat(movie.getImdb().getId()).isEqualTo(response.getImdbId());
            assertThat(movie.getImdb().getScores()).isEqualTo(response.getImdbVotes());
            assertThat(movie.getImdb().getWeightedAverage()).isEqualTo(response.getImdbRating());
        }
        if (movie.getMetacritic() != null) {
            assertThat(movie.getMetacritic().getMetascore()).isEqualTo(response.getMetascore());
        }
        if (movie.getTomatoes() != null) {
            assertThat(movie.getTomatoes().getTomatometer() + "%").isEqualTo(response.getRatings().get("Rotten Tomatoes"));
        }
    }

    @Test
    void givenNull_whenMap_thenThrows() {
        assertThatThrownBy(() -> OmdbMovieMapper.getInstance().map(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void givenNull_whenPatch_thenThrows() {
        assertThatThrownBy(() -> OmdbMovieMapper.getInstance().patch(fakeResponse(), null))
                .isInstanceOf(NullPointerException.class);
    }

    private OmdbResponse fakeResponse() {
        try {
            return new ObjectMapper().readValue(new ClassPathResource("stubs/omdb/movie.json").getFile(), OmdbResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}