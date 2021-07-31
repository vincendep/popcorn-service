package it.vincendep.popcorn.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MovieRating extends ShowRating {}


