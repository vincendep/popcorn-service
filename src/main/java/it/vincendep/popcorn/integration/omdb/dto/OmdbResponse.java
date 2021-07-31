package it.vincendep.popcorn.integration.omdb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class OmdbResponse {

    @JsonIgnore
    private Boolean response;
    @JsonIgnore
    private String error;

    @JsonSetter("Response")
    public void setResponse(Boolean response) {
        this.response = response;
    }

    @JsonSetter("Error")
    public void setError(String error) {
        this.error = error;
    }
}
