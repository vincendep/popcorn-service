package it.vincendep.popcorn.integration.job.tv;

import it.vincendep.popcorn.common.AsyncItemProcessListener;
import it.vincendep.popcorn.common.AsyncItemProcessor;
import it.vincendep.popcorn.common.AsyncItemWriteListener;
import it.vincendep.popcorn.common.AsyncItemWriter;
import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.core.TvShow;
import it.vincendep.popcorn.integration.job.common.PopcornJobSkipPolicy;
import it.vincendep.popcorn.integration.tmdb.export.TmdbTvShowExportFileItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
public class TvShowJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job tvShowJob(
            @Qualifier("importTvShowFromTmdbStep") Step importFromTmdbStep,
            @Qualifier("importTvShowFromOmdbStep") Step importFromOmdbStep) {
        return jobBuilderFactory.get("tvShowJob")
                .start(importFromTmdbStep)
                .next(importFromOmdbStep)
                .build();
    }

    @Bean
    public Step importTvShowFromTmdbStep(
            PopcornJobSkipPolicy skipPolicy,
            JsonItemReader<TmdbTvShowExportFileItem> reader,
            AsyncItemProcessor<TmdbTvShowExportFileItem, TvShow> processor,
            AsyncItemWriter<TvShow> writer,
            AsyncItemProcessListener<TmdbTvShowExportFileItem, TvShow> processListener,
            AsyncItemWriteListener<Movie> writeListener
    ) {
        return stepBuilderFactory.get("importTvShowFromTmdbStep")
                .<TmdbTvShowExportFileItem, ListenableFuture<TvShow>>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(processListener)
                .listener(writeListener)
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .build();
    }

    @Bean
    public Step importTvShowFromOmdbStep(
            PopcornJobSkipPolicy skipPolicy,
            ItemReader<TvShow> reader,
            AsyncItemProcessor<TvShow, TvShow> processor,
            AsyncItemWriter<TvShow> writer,
            AsyncItemProcessListener<TvShow, TvShow> processListener,
            AsyncItemWriteListener<TvShow> writeListener
    ) {
        return stepBuilderFactory.get("importTvShowFromOmdbStep")
                .<TvShow, ListenableFuture<TvShow>>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(processListener)
                .listener(writeListener)
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .build();
    }
}
