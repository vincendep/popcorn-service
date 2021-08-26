package it.vincendep.popcorn.integration.omdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class OmdbMovieResponse extends OmdbResponse {

    @JsonProperty("DVD")
    private String dvdRelease;
    @JsonProperty("BoxOffice")
    private String boxOffice;
    @JsonProperty("Production")
    private String[] production;
    @JsonProperty("Website")
    private String website;

    @JsonSetter("Production")
    public void setProduction(String production) {
        if (production != null) {
            this.production = production.split(", ");
        }
    }
}
