package it.vincendep.popcorn.integration.job;

import it.vincendep.popcorn.common.JacksonJsonObjectReader;
import it.vincendep.popcorn.common.MongoItemWriter;
import it.vincendep.popcorn.core.MovieRating;
import it.vincendep.popcorn.integration.omdb.dto.OmdbMovieResponse;
import it.vincendep.popcorn.integration.omdb.dto.OmdbResponseMapper;
import it.vincendep.popcorn.integration.omdb.exception.OmdbException;
import it.vincendep.popcorn.integration.omdb.service.OmdbService;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbResponseMapper;
import it.vincendep.popcorn.integration.tmdb.exception.TmdbException;
import it.vincendep.popcorn.integration.tmdb.export.TmdbExportFile;
import it.vincendep.popcorn.integration.tmdb.export.TmdbExportResourceResolver;
import it.vincendep.popcorn.integration.tmdb.export.TmdbMovieExportFileItem;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job popcornJob(
            @Qualifier("importFromTmdbStep") Step importFromTmdbStep,
            @Qualifier("importFromOmdbStep") Step importFromOmdbStep) {
        return jobBuilderFactory.get("popcornJob")
                .start(importFromTmdbStep)
                .next(importFromOmdbStep)
                .build();
    }

    @Bean
    public Step importFromTmdbStep(
            JsonItemReader<TmdbMovieExportFileItem> reader,
            AsyncItemProcessor<TmdbMovieExportFileItem, MovieRating> processor,
            AsyncItemWriter<MovieRating> writer
    ) {
        return stepBuilderFactory.get("importFromTmdbStep")
                .<TmdbMovieExportFileItem, Future<MovieRating>>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(TmdbException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .build();
    }

    @Bean
    public Step importFromOmdbStep(
            ItemReader<MovieRating> reader,
            AsyncItemProcessor<MovieRating, MovieRating> processor,
            AsyncItemWriter<MovieRating> writer
    ) {
        return stepBuilderFactory.get("importFromOmdbStep")
                .<MovieRating, Future<MovieRating>>chunk(1000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skip(OmdbException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .build();
    }

    @Bean
    @StepScope
    public JsonItemReader<TmdbMovieExportFileItem> tmdbMovieExportFileItemReader(TmdbExportResourceResolver resourceResolver) throws IOException {
        return new JsonItemReaderBuilder<TmdbMovieExportFileItem>()
                .name("tmdbMovieExportFileItemReader")
                .resource(resourceResolver.getResource(TmdbExportFile.MOVIES))
                .jsonObjectReader(new JacksonJsonObjectReader<>(TmdbMovieExportFileItem.class))
                .build();
    }

    @Bean
    public MongoItemReader<MovieRating> movieRatingReader(MongoOperations template) {
        return new MongoItemReaderBuilder<MovieRating>()
                .name("mongoMovieRatingReader")
                .collection(MovieRating.COLLECTION_NAME)
                .template(template)
                .targetType(MovieRating.class)
                .query(Query.query(Criteria.where("_id").ne(null)))
                .sorts(Map.of())
                .build();
    }

    @Bean
    public ItemProcessor<TmdbMovieExportFileItem, MovieRating> tmdbProcessor(TmdbService tmdbService) {
        return item -> {
            TmdbMovieResponse response = tmdbService.getMovieDetails(item.getId()).block();
            if (response != null) {
                return TmdbResponseMapper.map(response);
            }
            return null;
        };
    }

    @Bean
    public ItemProcessor<MovieRating, MovieRating> omdbProcessor(OmdbService omdbService) {
        return movieRating -> {
            OmdbMovieResponse response = omdbService.getById(movieRating.getImdb().getId()).block();
            if (response != null) {
                return OmdbResponseMapper.map(response, movieRating);
            }
            return null;
        };
    }

    @Bean
    public AsyncItemProcessor<MovieRating, MovieRating> asyncOmdbProcessor(
            ItemProcessor<MovieRating, MovieRating> itemProcessor,
            TaskExecutor taskExecutor
    ) {
        AsyncItemProcessor<MovieRating, MovieRating> processor = new AsyncItemProcessor<>();
        processor.setTaskExecutor(taskExecutor);
        processor.setDelegate(itemProcessor);
        return processor;
    }

    @Bean
    public AsyncItemProcessor<TmdbMovieExportFileItem, MovieRating> asyncTmdbProcessor(
            ItemProcessor<TmdbMovieExportFileItem, MovieRating> itemProcessor,
            TaskExecutor taskExecutor
    ) {
        AsyncItemProcessor<TmdbMovieExportFileItem, MovieRating> processor = new AsyncItemProcessor<>();
        processor.setTaskExecutor(taskExecutor);
        processor.setDelegate(itemProcessor);
        return processor;
    }

    @Bean
    public AsyncItemWriter<MovieRating> asyncItemWriter(ItemWriter<MovieRating> itemWriter) {
        AsyncItemWriter<MovieRating> writer = new AsyncItemWriter<>();
        writer.setDelegate(itemWriter);
        return writer;
    }

    @Bean
    public ItemWriter<MovieRating> movieRatingWriter(MongoOperations template) {
        var writer = new MongoItemWriter<MovieRating>();
        writer.setCollection(MovieRating.COLLECTION_NAME);
        writer.setQuery(movieRating -> Query.query(new Criteria().orOperator(
                Criteria.where("_id").is(movieRating.getId()),
                Criteria.where("tmdb._id").is(movieRating.getTmdb().getId()))));
        writer.setTemplate(template);
        return writer;
    }
}
