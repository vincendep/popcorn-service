package it.vincendep.popcorn.model.rating;

import lombok.Data;

@Data
public class Metacritic {

    private Integer metascore;
    private Integer criticReviews;
    private Integer userScore;
    private Integer userReviews;

    public Metacritic(Integer metascore) {
        this.metascore = metascore;
    }
}
