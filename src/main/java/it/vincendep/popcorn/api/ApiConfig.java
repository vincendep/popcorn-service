package it.vincendep.popcorn.api;

import it.vincendep.popcorn.common.QueryParameterExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ApiConfig {

    @Bean
    public QueryParameterExtractor reactiveParameterExtractor() {
        return new QueryParameterExtractor();
    }
}
