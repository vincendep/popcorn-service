package it.vincendep.popcorn.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tomatoes {

    private Integer tomatometer;
    private Integer positiveReviews;
    private Integer negativeReviews;
    private Integer audienceScore;
    private Integer ratings;
    private Boolean certifiedFresh;

    public Tomatoes(Tomatoes other) {
        this.tomatometer = other.tomatometer;
        this.positiveReviews = other.positiveReviews;
        this.negativeReviews = other.negativeReviews;
        this.audienceScore = other.audienceScore;
        this.ratings = other.ratings;
        this.certifiedFresh = other.certifiedFresh;
    }
}
