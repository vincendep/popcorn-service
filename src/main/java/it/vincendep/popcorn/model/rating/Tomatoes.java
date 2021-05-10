package it.vincendep.popcorn.model.rating;

import lombok.Data;

@Data
public class Tomatoes {

    private Integer tomatometer;
    private Integer positiveReviews;
    private Integer negativeReviews;
    private Integer audienceScore;
    private Integer ratings;
    private Boolean certifiedFresh;
}
