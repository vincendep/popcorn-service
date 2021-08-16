package it.vincendep.popcorn.api.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import it.vincendep.popcorn.core.MovieRating;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PopcornResponse<T> {

    @JsonUnwrapped
    private final T response;
    @JsonUnwrapped
    private final MovieRating rating;
}
