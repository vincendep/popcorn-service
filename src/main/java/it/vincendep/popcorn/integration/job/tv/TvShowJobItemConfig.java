package it.vincendep.popcorn.integration.job.tv;

import it.vincendep.popcorn.common.AsyncItemProcessor;
import it.vincendep.popcorn.common.AsyncItemWriter;
import it.vincendep.popcorn.common.JacksonJsonObjectReader;
import it.vincendep.popcorn.common.MongoItemWriter;
import it.vincendep.popcorn.core.TvShow;
import it.vincendep.popcorn.integration.omdb.dto.OmdbResponse;
import it.vincendep.popcorn.integration.omdb.mapper.OmdbTvShowMapper;
import it.vincendep.popcorn.integration.omdb.service.OmdbService;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbAppendableResponse;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbTvShowResponse;
import it.vincendep.popcorn.integration.tmdb.export.TmdbExportFile;
import it.vincendep.popcorn.integration.tmdb.export.TmdbExportResourceResolver;
import it.vincendep.popcorn.integration.tmdb.export.TmdbTvShowExportFileItem;
import it.vincendep.popcorn.integration.tmdb.mapper.TmdbTvShowMapper;
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
public class TvShowJobItemConfig {

    @Bean
    @StepScope
    public JsonItemReader<TmdbTvShowExportFileItem> tmdbTvShowExportFileItemReader(TmdbExportResourceResolver resourceResolver) throws IOException {
        return new JsonItemReaderBuilder<TmdbTvShowExportFileItem>()
                .name("tmdbTvShowExportFileItemReader")
                .resource(resourceResolver.getResource(TmdbExportFile.TV_SHOW))
                .jsonObjectReader(new JacksonJsonObjectReader<>(TmdbTvShowExportFileItem.class))
                .build();
    }

    @Bean
    public MongoItemReader<TvShow> tvShowReader(MongoOperations template) {
        return new MongoItemReaderBuilder<TvShow>()
                .name("tvShowReader")
                .collection(TvShow.COLLECTION_NAME)
                .template(template)
                .targetType(TvShow.class)
                .query(new Query())
                .sorts(Map.of())
                .build();
    }

    @Bean
    public ItemProcessor<TmdbTvShowExportFileItem, TvShow> tmdbTvShowProcessor(TmdbService tmdbService) {
        return item -> {
            TmdbTvShowResponse response = tmdbService.getTvShowDetails(item.getId())
                    .map(TmdbAppendableResponse::getResponse)
                    .block();
            if (response != null) {
                return TmdbTvShowMapper.getInstance().map(response);
            }
            return null;
        };
    }

    @Bean
    public AsyncItemProcessor<TmdbTvShowExportFileItem, TvShow> asyncTmdbTvShowProcessor(ItemProcessor<TmdbTvShowExportFileItem, TvShow> delegate, TaskExecutor taskExecutor) {
        AsyncItemProcessor<TmdbTvShowExportFileItem, TvShow> processor = new AsyncItemProcessor<>();
        processor.setDelegate(delegate);
        processor.setTaskExecutor(taskExecutor);
        return processor;
    }

    @Bean
    public ItemProcessor<TvShow, TvShow> omdbTvShowProcessor(OmdbService omdbService) {
        return tvShow -> {
            OmdbResponse response = omdbService.getById(tvShow.getImdb().getId()).block();
            if (response != null) {
                return OmdbTvShowMapper.getInstance().patch(response, new TvShow(tvShow));
            }
            return null;
        };
    }

    @Bean
    public AsyncItemProcessor<TvShow, TvShow> asyncOmdbTvShowProcessor(ItemProcessor<TvShow, TvShow> delegate, TaskExecutor taskExecutor) {
        AsyncItemProcessor<TvShow, TvShow> processor = new AsyncItemProcessor<>();
        processor.setDelegate(delegate);
        processor.setTaskExecutor(taskExecutor);
        return processor;
    }

    @Bean
    public ItemWriter<TvShow> tvShowWriter(MongoOperations template) {
        var writer = new MongoItemWriter<TvShow>();
        writer.setCollection(TvShow.COLLECTION_NAME);
        writer.setQuery(tvShow -> Query.query(new Criteria().orOperator(
                Criteria.where("_id").is(tvShow.getId()),
                Criteria.where("tmdb._id").is(tvShow.getTmdb().getId()))));
        writer.setTemplate(template);
        return writer;
    }

    @Bean
    public AsyncItemWriter<TvShow> asyncTvShowWriter(ItemWriter<TvShow> delegate) {
        AsyncItemWriter<TvShow> writer = new AsyncItemWriter<>();
        writer.setDelegate(delegate);
        return writer;
    }
}
