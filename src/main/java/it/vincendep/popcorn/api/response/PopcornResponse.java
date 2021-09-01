package it.vincendep.popcorn.api.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import it.vincendep.popcorn.core.Movie;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PopcornResponse<T, F> {

    @JsonUnwrapped
    private final T response;
    private final F popcornData;
}
