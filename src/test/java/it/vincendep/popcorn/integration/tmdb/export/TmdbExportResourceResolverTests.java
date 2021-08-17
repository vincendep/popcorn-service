package it.vincendep.popcorn.integration.tmdb.export;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;

import static it.vincendep.popcorn.integration.tmdb.export.TmdbExportResourceResolver.TMDB_DAILY_EXPORT_HOUR;
import static it.vincendep.popcorn.integration.tmdb.export.TmdbExportResourceResolver.TMDB_DAILY_EXPORT_ZONE_OFFSET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(SpringExtension.class)
public class TmdbExportResourceResolverTests {

    private final TmdbExportResourceResolver tmdbExportResourceResolver = new TmdbExportResourceResolver();

    @Test
    void whenGetResource_thenIsEqualToTodayResource() throws IOException {
        for (TmdbExportFile exportFile: TmdbExportFile.values()) {
            Resource resource = tmdbExportResourceResolver.getResource(exportFile);
            Resource todayResource = tmdbExportResourceResolver.getResource(exportFile, Instant.now());
            assertThat(resource, equalTo(todayResource));
        }
    }

    @Test
    void givenToday_whenGetResource_thenExistsAndIsReadable() throws IOException {
        for (TmdbExportFile exportFile: TmdbExportFile.values()) {
            Resource resource = tmdbExportResourceResolver.getResource(exportFile);
            assertThat(resource.exists(), is(true));
            assertThat(resource.isReadable(), is(true));
        }
    }

    @Test
    void givenTomorrow_whenGetResource_thenThrows() {
        Instant tomorrow = LocalDateTime
                .now(TMDB_DAILY_EXPORT_ZONE_OFFSET)
                .plusDays(1L)
                .withHour(TMDB_DAILY_EXPORT_HOUR)
                .toInstant(TMDB_DAILY_EXPORT_ZONE_OFFSET);
        for (TmdbExportFile exportFile: TmdbExportFile.values()) {
            try {
                tmdbExportResourceResolver.getResource(exportFile, tomorrow);
                fail();
            } catch (IOException ignored) {}
        }
    }
}
