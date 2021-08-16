package it.vincendep.popcorn.integration.tmdb.exception;

public class TmdbException extends RuntimeException {

    public TmdbException() {
        super();
    }

    public TmdbException(String message) {
        super(message);
    }

    public TmdbException(String message, Throwable cause) {
        super(message, cause);
    }
}
