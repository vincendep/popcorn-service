package it.vincendep.popcorn.integration.tmdb.export;

import it.vincendep.popcorn.common.GZIPResource;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Primary
@Component
public class TmdbExportResourceResolver {

    static final String URL_FORMAT = "http://files.tmdb.org/p/exports/%s_%2$tm_%2$td_%2$tY.json.gz";
    static final int TMDB_DAILY_EXPORT_HOUR = 8;
    static final ZoneOffset TMDB_DAILY_EXPORT_ZONE_OFFSET = ZoneOffset.UTC;

    public Resource getResource(TmdbExportFile tmdbExportFile) throws IOException {
        return getResource(tmdbExportFile, Instant.now());
    }

    public Resource getResource(TmdbExportFile tmdbExportFile, Instant instant) throws IOException {
        LocalDateTime exportDate = LocalDateTime.ofInstant(instant, TMDB_DAILY_EXPORT_ZONE_OFFSET);
        if (exportDate.getHour() < TMDB_DAILY_EXPORT_HOUR) {
            exportDate = exportDate.minusDays(1);
        }
        return createResource(String.format(URL_FORMAT, tmdbExportFile.filePrefix(), exportDate));
    }

    protected Resource createResource(String url) throws IOException {
        File tempFile = File.createTempFile(Instant.now().toString(), "txt");
        FileUtils.copyURLToFile(new URL(url), tempFile);
        return new GZIPResource(new FileSystemResource(tempFile));
    }
}
