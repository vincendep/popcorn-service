package it.vincendep.popcorn.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
public class MongoUtilsTests {

    @Autowired
    private MongoTemplate template;

    @Test
    void givenDocument_whenUpdateFrom_thenUpdateOnlyNotNullValues() {}

    @Test
    void givenNull_whenUpdateFrom_thenThrows() {
        assertThatThrownBy(() -> MongoUtils.updateFrom(null));
    }
}
