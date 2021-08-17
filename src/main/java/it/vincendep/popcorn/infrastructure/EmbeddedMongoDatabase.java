package it.vincendep.popcorn.infrastructure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EmbeddedMongoAutoConfiguration.class)
@ConditionalOnProperty("popcorn.mongodb.embedded.enabled")
public class EmbeddedMongoDatabase {}
