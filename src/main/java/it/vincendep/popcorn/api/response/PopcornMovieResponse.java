package it.vincendep.popcorn.api.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import it.vincendep.popcorn.core.MovieRating;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PopcornMovieResponse<T> {

    @JsonUnwrapped
    private final T movieResponse;
    @JsonUnwrapped
    private final MovieRating movieRating;
}
