package it.vincendep.popcorn.integration.tmdb.service;

import it.vincendep.popcorn.common.JsonUtils;
import it.vincendep.popcorn.common.ResourceCleaner;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.zip.GZIPInputStream;

import static java.lang.String.format;

public class TmdbExportUtils {

    private static final String EXPORT_FILE_HOST = "http://files.tmdb.org";
    private static final String EXPORT_FILE_NAME_FORMAT = "%s_%2$tm_%2$td_%2$tY.json.gz";
    private static final ZoneId EXPORT_ZONE_OFFSET = ZoneOffset.UTC;
    private static final int EXPORT_HOUR = 8;

    public static Flux<Long> fluxFromExportFile() {
        LocalDate lastExportDate = getLastExportDate(LocalDateTime.now(EXPORT_ZONE_OFFSET));
        return Flux.using(
                () -> new BufferedReader(new InputStreamReader(new GZIPInputStream(new URL(getExportFileUrl(lastExportDate)).openStream()))),
                source -> Flux.fromStream(source.lines()).map(line -> JsonUtils.<Integer>getValue(line, "id").longValue()),
                new ResourceCleaner());
    }

    private static String getExportFileUrl(LocalDate date) {
        return EXPORT_FILE_HOST + "/p/exports/" + format(EXPORT_FILE_NAME_FORMAT, "movie_ids", date);
    }

    private static LocalDate getLastExportDate(LocalDateTime today) {
        if (today.getHour() < EXPORT_HOUR) {
            return today.minusDays(1).toLocalDate();
        }
        return today.toLocalDate();
    }
}
