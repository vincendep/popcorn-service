package it.vincendep.popcorn.job;


import it.vincendep.popcorn.integration.tmdb.job.TmdbJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class TmdbJobTest {

    @Autowired
    private TmdbJob tmdbFileExporter;

    @Test
    void givenAnExportFile_whenGetFileName_thenIsCorrect() {

    }
}
