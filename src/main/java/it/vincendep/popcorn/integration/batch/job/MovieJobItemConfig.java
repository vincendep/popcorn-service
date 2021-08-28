package it.vincendep.popcorn.integration.batch.job;

import it.vincendep.popcorn.common.AsyncItemProcessor;
import it.vincendep.popcorn.common.AsyncItemWriter;
import it.vincendep.popcorn.common.JacksonJsonObjectReader;
import it.vincendep.popcorn.common.MongoItemWriter;
import it.vincendep.popcorn.core.Movie;
import it.vincendep.popcorn.integration.omdb.dto.OmdbResponse;
import it.vincendep.popcorn.integration.omdb.mapper.OmdbMovieMapper;
import it.vincendep.popcorn.integration.omdb.service.OmdbService;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import it.vincendep.popcorn.integration.tmdb.export.TmdbExportFile;
import it.vincendep.popcorn.integration.tmdb.export.TmdbExportResourceResolver;
import it.vincendep.popcorn.integration.tmdb.export.TmdbMovieExportFileItem;
import it.vincendep.popcorn.integration.tmdb.mapper.TmdbMovieMapper;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.util.Map;

@Configuration
public class MovieJobItemConfig {

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
    public MongoItemReader<Movie> movieReader(MongoOperations template) {
        return new MongoItemReaderBuilder<Movie>()
                .name("movieReader")
                .collection(Movie.COLLECTION_NAME)
                .template(template)
                .targetType(Movie.class)
                .query(Query.query(Criteria.where("_id").ne(null)))
                .sorts(Map.of())
                .build();
    }

    @Bean
    public ItemProcessor<TmdbMovieExportFileItem, Movie> tmdbMovieProcessor(TmdbService tmdbService) {
        return item -> {
            TmdbMovieResponse response = tmdbService.getMovieDetails(item.getId()).block();
            if (response != null) {
                return TmdbMovieMapper.getInstance().map(response);
            }
            return null;
        };
    }

    @Bean
    public ItemProcessor<Movie, Movie> omdbMovieProcessor(OmdbService omdbService) {
        return movieRating -> {
            OmdbResponse response = omdbService.getById(movieRating.getImdb().getId()).block();
            if (response != null) {
                return OmdbMovieMapper.getInstance().patch(response, new Movie(movieRating));
            }
            return null;
        };
    }

    @Bean
    public AsyncItemProcessor<Movie, Movie> asyncOmdbMovieProcessor(
            ItemProcessor<Movie, Movie> itemProcessor,
            TaskExecutor taskExecutor
    ) {
        AsyncItemProcessor<Movie, Movie> processor = new AsyncItemProcessor<>();
        processor.setTaskExecutor(taskExecutor);
        processor.setDelegate(itemProcessor);
        return processor;
    }

    @Bean
    public AsyncItemProcessor<TmdbMovieExportFileItem, Movie> asyncTmdbMovieProcessor(
            ItemProcessor<TmdbMovieExportFileItem, Movie> itemProcessor,
            TaskExecutor taskExecutor
    ) {
        AsyncItemProcessor<TmdbMovieExportFileItem, Movie> processor = new AsyncItemProcessor<>();
        processor.setTaskExecutor(taskExecutor);
        processor.setDelegate(itemProcessor);
        return processor;
    }

    @Bean
    public AsyncItemWriter<Movie> asyncMovieWriter(ItemWriter<Movie> itemWriter) {
        AsyncItemWriter<Movie> writer = new AsyncItemWriter<>();
        writer.setDelegate(itemWriter);
        return writer;
    }

    @Bean
    public ItemWriter<Movie> movieWriter(MongoOperations template) {
        var writer = new MongoItemWriter<Movie>();
        writer.setCollection(Movie.COLLECTION_NAME);
        writer.setQuery(movieRating -> Query.query(new Criteria().orOperator(
                Criteria.where("_id").is(movieRating.getId()),
                Criteria.where("tmdb._id").is(movieRating.getTmdb().getId()))));
        writer.setTemplate(template);
        return writer;
    }
}
