package it.vincendep.popcorn.integration.tmdb.job;

import it.vincendep.popcorn.integration.tmdb.config.TmdbProperties;
import it.vincendep.popcorn.integration.tmdb.dto.TmdbMovieResponse;
import it.vincendep.popcorn.integration.tmdb.service.TmdbService;
import it.vincendep.popcorn.utils.JsonUtils;
import it.vincendep.popcorn.utils.ResourceCleaner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.retry.RetryContext;

import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.function.Predicate;
import java.util.zip.GZIPInputStream;

import static java.time.Duration.ofSeconds;
import static reactor.retry.Retry.onlyIf;
import static reactor.util.retry.Retry.withThrowable;

@Slf4j
@Component
@RequiredArgsConstructor
public class TmdbJob {

 	private static final String EXPORT_FILE_NAME_FORMAT = "%s_%2$tm_%2$td_%2$tY.json.gz";
 	private static final ZoneId EXPORT_ZONE_OFFSET = ZoneOffset.UTC;
	private static final int EXPORT_HOUR = 8;

	private final TmdbProperties tmdbProperties;
    private final TmdbService tmdbService;

    public Flux<TmdbMovieResponse> getAllMovieDetails() {
    	var fileName = String.format(EXPORT_FILE_NAME_FORMAT, "movie_ids", getLastExportDate());
    	return getMoviesDetails(extractIdsFromFile(fileName));
    }

    public Flux<TmdbMovieResponse> getMoviesDetails(Flux<Long> tmdbIds) {
    	return tmdbIds.flatMap(id ->
				tmdbService.getMovieDetails(id)
						.doOnEach(signal -> logRemoteCall(signal, id) )
						.retryWhen(withThrowable(onlyIf(isConnectionError()).fixedBackoff(ofSeconds(10))))
						.onErrorResume(whenErrorIs404(), e -> Mono.empty()));
    }

    private void logRemoteCall(Signal<TmdbMovieResponse> signal, Long movieId) {
    	if (signal.isOnError()) {
    		log.error("Error getting details for movie with id {}", movieId, signal.getThrowable());
		} else if (signal.isOnNext()) {
    		log.info("Successfully got data for movie with id {}: {}", movieId, signal.get());
		}
	}

    private Flux<Long> extractIdsFromFile(String fileName) {
		String fileUrl = tmdbProperties.getFileHost() + "/p/exports/" + fileName;
		return Flux.using(
				() -> new BufferedReader(new InputStreamReader(new GZIPInputStream(new URL(fileUrl).openStream()))),
				br -> Flux.fromStream(br.lines()).map(line -> JsonUtils.<Integer>getValue(line, "id").longValue()),
				new ResourceCleaner());
	}

	private LocalDate getLastExportDate() {
    	LocalDateTime today = LocalDateTime.now(EXPORT_ZONE_OFFSET);
    	if (today.getHour() < EXPORT_HOUR) {
    		return today.minusDays(1).toLocalDate();
		}
    	return today.toLocalDate();
	}

	private Predicate<RetryContext<?>> isConnectionError() {
    	return (RetryContext<?> ctx) -> {
			if (ctx.exception() instanceof WebClientRequestException) {
				WebClientRequestException requestException = (WebClientRequestException) ctx.exception();
				return requestException.getRootCause() instanceof SSLException;
			}
			return false;
		};
	}

	private <E extends Throwable> Predicate<E> whenErrorIs404() {
    	return ex -> {
			if (ex instanceof WebClientResponseException) {
				WebClientResponseException responseException = (WebClientResponseException) ex;
				return responseException.getRawStatusCode() == 404;
			}
			return false;
		};
	}
}
