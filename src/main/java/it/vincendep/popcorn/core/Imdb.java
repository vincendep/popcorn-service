package it.vincendep.popcorn.core;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Imdb {

    @Id
    private String id;
    private Float weightedAverage;
    private Long scores;

    public Imdb(Imdb other) {
        this.id = other.id;
        this.weightedAverage = other.weightedAverage;
        this.scores = other.scores;
    }
}
