package it.vincendep.popcorn.integration.tmdb.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.vincendep.popcorn.integration.tmdb.config.TmdbConfig;
import it.vincendep.popcorn.integration.tmdb.config.TmdbProperties;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ContextConfiguration(classes = {TmdbConfig.class, TmdbProperties.class})
public class TmdbServiceTest {

    @Autowired
    private TmdbConfig tmdbConfig;
    @Autowired
    private TmdbProperties tmdbProperties;

    @Test
    public void test() {
        assertThat(tmdbConfig, is(notNullValue()));
        assertThat(tmdbProperties.getApiHost(), is(notNullValue()));
    }
}
