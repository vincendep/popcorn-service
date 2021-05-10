package it.vincendep.popcorn.batch;

import it.vincendep.popcorn.PopcornApplication;
import it.vincendep.popcorn.integration.imdb.ImdbJob;
import it.vincendep.popcorn.integration.imdb.RatingData;
import it.vincendep.popcorn.integration.tmdb.job.TmdbJob;
import it.vincendep.popcorn.integration.tmdb.mapper.MovieMapper;
import it.vincendep.popcorn.model.show.Movie;
import it.vincendep.popcorn.model.show.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Slf4j
@Import(value = PopcornApplication.class)
@RequiredArgsConstructor
public class JobLauncher<T> implements ApplicationRunner {

    private final ApplicationContext context;
    private final TmdbJob tmdbJob;
    private final ImdbJob imdbJob;
    private final MovieRepository movieRepository;


    public static void main(String[] argv) {
        new SpringApplicationBuilder(JobLauncher.class)
                .web(WebApplicationType.NONE)
                .bannerMode(Mode.OFF)
                .run(argv);
    }

    @Override
    public void run(ApplicationArguments args) {

        tmdbJob.getAllMovieDetails()
                .map(MovieMapper::map)
                .take(1000)
                .buffer(1000)
                .flatMap(movieRepository::saveAll)
                .subscribe();

        imdbJob.getMovieRatings(movieRepository.findAll()
                .filter(movie -> isNotBlank(movie.getImdbId()))
                .map(Movie::getImdbId))
                .log()
                .flatMap(rating -> movieRepository.findByImdbId(rating.getImdbId())
                        .doOnNext(movie -> applyRating(movie, rating)))
                .buffer(1000)
                .flatMap(movieRepository::saveAll);
    }

    private void applyRating(Movie movie, RatingData ratingData) {
        movie.setImdb(ratingData.getImdb());
        movie.setMetacritic(ratingData.getMetacritic());
    }
}
