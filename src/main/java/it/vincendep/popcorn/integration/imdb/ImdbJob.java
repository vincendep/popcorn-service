package it.vincendep.popcorn.integration.imdb;

import it.vincendep.popcorn.model.rating.Imdb;
import it.vincendep.popcorn.model.rating.Metacritic;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
public class ImdbJob {

    public static final String IMDB_URL = "https://www.imdb.com/title/{imdbId}/";

    public Flux<RatingData> getMovieRatings(Flux<String> imdbIds) {

        WebClient imdbClient = WebClient.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .baseUrl(IMDB_URL)
                .build();

        return imdbIds.log().flatMap(imdbId -> imdbClient
                        .get()
                        .uri(builder -> builder.build(imdbId))
                        .retrieve()
                        .bodyToMono(String.class)
                        .map(html -> {
                            RatingData result = new RatingData();
                            result.setImdbId(imdbId);
                            Document document = Jsoup.parse(html);
                            Optional.of(document.getElementsByAttributeValue("data-testid", "hero-rating-bar__aggregate-rating__score"))
                                    .filter(elements -> !elements.isEmpty())
                                    .map(elements -> elements.get(0).getElementsByTag("span").get(0))
                                    .ifPresent(element -> result.setImdb(new Imdb(Float.valueOf(element.ownText()))));
                            Optional.of(document.getElementsByAttributeValue("data-testid", "reviewContent-all-reviews"))
                                    .filter(elements -> !elements.isEmpty())
                                    .map(elements -> elements.get(0))
                                    .map(elements -> elements.getElementsByClass("score-meta"))
                                    .filter(elements -> !elements.isEmpty())
                                    .map(elements -> elements.get(0))
                                    .ifPresent(element -> result.setMetacritic(new Metacritic(Integer.valueOf(element.ownText()))));
                            return result;
                        })
                );
    }
}
