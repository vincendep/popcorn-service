package it.vincendep.popcorn.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tmdb {

	private Long id;
	private Float score;
	private Long voteCount;

	public Tmdb(Tmdb other) {
		this.id = other.id;
		this.score = other.score;
		this.voteCount = other.voteCount;
	}
}
