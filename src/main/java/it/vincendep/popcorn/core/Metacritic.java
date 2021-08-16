package it.vincendep.popcorn.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Metacritic {

    private Integer metascore;
    private Integer criticReviews;
    private Integer userScore;
    private Integer userReviews;

    public Metacritic(Metacritic other) {
        this.metascore = other.metascore;
        this.criticReviews = other.criticReviews;
        this.userScore = other.userScore;
        this.userReviews = other.userReviews;
    }
}
