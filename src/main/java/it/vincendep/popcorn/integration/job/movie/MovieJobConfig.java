package it.vincendep.popcorn.integration.job.movie;

import it.vincendep.popcorn.common.AsyncItemProcessListener;
import it.vincendep.popcorn.common.AsyncItemProcessor;
import it.vincendep.popcorn.common.AsyncItemWriteListener;
import it.vincendep.popcorn.common.AsyncItemWriter;
import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.integration.job.common.PopcornJobSkipPolicy;
import it.vincendep.popcorn.integration.tmdb.export.TmdbMovieExportFileItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.concurrent.ListenableFuture;

@Configuration
@RequiredArgsConstructor
public class MovieJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job movieJob(
            @Qualifier("importMovieFromTmdbStep") Step importFromTmdbStep,
            @Qualifier("importMovieFromOmdbStep") Step importFromOmdbStep) {
        return jobBuilderFactory.get("movieJob")
                .start(importFromTmdbStep)
                .next(importFromOmdbStep)
                .build();
    }

    @Bean
    public Step importMovieFromTmdbStep(
            PopcornJobSkipPolicy skipPolicy,
            JsonItemReader<TmdbMovieExportFileItem> reader,
            AsyncItemProcessor<TmdbMovieExportFileItem, Movie> processor,
            AsyncItemWriter<Movie> writer,
            AsyncItemProcessListener<TmdbMovieExportFileItem, Movie> processListener,
            AsyncItemWriteListener<Movie> writeListener
    ) {
        return stepBuilderFactory.get("importMovieFromTmdbStep")
                .<TmdbMovieExportFileItem, ListenableFuture<Movie>>chunk(1000)
                .listener(processListener)
                .listener(writeListener)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .build();
    }

    @Bean
    public Step importMovieFromOmdbStep(
            PopcornJobSkipPolicy skipPolicy,
            ItemReader<Movie> reader,
            AsyncItemProcessor<Movie, Movie> processor,
            AsyncItemWriter<Movie> writer,
            AsyncItemProcessListener<Movie, Movie> processListener,
            AsyncItemWriteListener<Movie> writeListener
    ) {
        return stepBuilderFactory.get("importMovieFromOmdbStep")
                .<Movie, ListenableFuture<Movie>>chunk(1000)
                .listener(processListener)
                .listener(writeListener)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .build();
    }
}
