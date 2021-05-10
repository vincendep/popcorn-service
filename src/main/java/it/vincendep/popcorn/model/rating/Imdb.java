package it.vincendep.popcorn.model.rating;

import lombok.Data;

@Data
public class Imdb {

    private Float weightedAverage;
    private Integer scores;

    public Imdb(Float weightedAverage) {
        this.weightedAverage = weightedAverage;
    }
}
