package it.vincendep.popcorn.integration.omdb.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class OmdbResponseTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenOmdbResponse_whenDeserialize_thenTypeIsCorrect() throws IOException {
        Resource movieJson = new ClassPathResource("stubs/omdb/movie.json");
        Resource tvShowJson = new ClassPathResource("stubs/omdb/tv-show.json");
        Resource tvEpisodeJson = new ClassPathResource("stubs/omdb/tv-episode.json");

        OmdbResponse movie = objectMapper.readValue(movieJson.getFile(), OmdbResponse.class);
        OmdbResponse tvShow = objectMapper.readValue(tvShowJson.getFile(), OmdbResponse.class);
        OmdbResponse tvEpisode = objectMapper.readValue(tvEpisodeJson.getFile(), OmdbResponse.class);

        assertThat(movie).isInstanceOf(OmdbMovieResponse.class);
        assertThat(tvShow).isInstanceOf(OmdbTvShowResponse.class);
        assertThat(tvEpisode).isInstanceOf(OmdbTvEpisodeResponse.class);
    }
}
