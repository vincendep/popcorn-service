package it.vincendep.popcorn.integration.tmdb.export;

public enum TmdbExportFile {

    MOVIES("movie_ids");

    private final String filePrefix;

    TmdbExportFile(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String filePrefix() {
        return filePrefix;
    }
}