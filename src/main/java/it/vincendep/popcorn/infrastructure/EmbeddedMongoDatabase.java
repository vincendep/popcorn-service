package it.vincendep.popcorn.infrastructure;

import it.vincendep.popcorn.PopcornApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(PopcornApplication.DEV)
@Import(EmbeddedMongoAutoConfiguration.class)
public class EmbeddedMongoDatabase {}
