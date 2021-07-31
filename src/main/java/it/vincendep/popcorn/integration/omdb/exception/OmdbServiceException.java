package it.vincendep.popcorn.integration.omdb.exception;

public class OmdbServiceException extends Exception {

    public OmdbServiceException() {
        super();
    }

    public OmdbServiceException(String message) {
        super(message);
    }

    public OmdbServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
