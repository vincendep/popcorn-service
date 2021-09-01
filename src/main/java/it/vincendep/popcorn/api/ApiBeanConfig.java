package it.vincendep.popcorn.api;

import it.vincendep.popcorn.common.QueryParameterExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiBeanConfig {

    @Bean
    public QueryParameterExtractor queryParameterExtractor() {
        var extractor = new QueryParameterExtractor();
        extractor.setPageParamName("skip");
        extractor.setSizeParamName("limit");
        return extractor;
    }
}
