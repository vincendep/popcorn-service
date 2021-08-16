package it.vincendep.popcorn.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MovieRating extends ShowRating {

    public static final String COLLECTION_NAME = "movieRating";

    public MovieRating(MovieRating other) {
        super(other);
    }
}


