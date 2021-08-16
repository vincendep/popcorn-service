package it.vincendep.popcorn.infrastructure;

import it.vincendep.popcorn.core.MovieRating;
import it.vincendep.popcorn.core.Tmdb;
import it.vincendep.popcorn.integration.job.JobConfig;
import it.vincendep.popcorn.integration.tmdb.export.TmdbExportResourceResolver;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBatchTest
@EnableAutoConfiguration
@SpringJUnitConfig(classes = {DataConfigurationTests.Config.class, JobConfig.class})
public class DataConfigurationTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private MongoOperations mongoTemplate;

    @Configuration
    static class Config {
        @Bean
        TmdbExportResourceResolver tmdbTestExportResourceResolver() {
            return new TmdbExportResourceResolver() {
                @Override
                protected Resource createResource(String url) throws MalformedURLException {
                    return new ClassPathResource("tmdb/movie-export.txt");
                }
            };
        }
    }

    @After
    public void tearDown() {
        jobRepositoryTestUtils.removeJobExecutions();
        mongoTemplate.dropCollection(MovieRating.class);
    }

    @Test
    void whenImportFromTmdbJob_thenComplete() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));

        List<Long> testIds = Arrays.asList(3924L, 25449L, 8773L, 6124L, 3924L);
        List<Long> importedIds = mongoTemplate.findAll(MovieRating.class)
                .stream()
                .map(MovieRating::getTmdb)
                .map(Tmdb::getId)
                .collect(Collectors.toList());

        assertThat(importedIds.containsAll(testIds), is(true));
    }

    @Test
    void whenImportsArePresent_thenSkipThem() throws Exception {

        Tmdb tmdb = new Tmdb();
        tmdb.setId(3924L);
        MovieRating movieRating = new MovieRating();
        movieRating.setTmdb(tmdb);
        mongoTemplate.insert(movieRating);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        StepExecution stepExecution = jobExecution
                .getStepExecutions()
                .stream()
                .filter(ex -> ex.getStepName().equals("importFromTmdbStep"))
                .findFirst()
                .get();
        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
        assertThat(stepExecution.getFilterCount(), is(1));
    }
}

