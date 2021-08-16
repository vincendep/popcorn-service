package it.vincendep.popcorn.integration.omdb.exception;

public class OmdbException extends RuntimeException {

    public OmdbException() {
        super();
    }

    public OmdbException(String message) {
        super(message);
    }

    public OmdbException(String message, Throwable cause) {
        super(message, cause);
    }
}
