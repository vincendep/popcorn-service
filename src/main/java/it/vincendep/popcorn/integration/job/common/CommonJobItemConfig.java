package it.vincendep.popcorn.integration.job.common;

import it.vincendep.popcorn.common.AsyncItemProcessListener;
import it.vincendep.popcorn.common.AsyncItemWriteListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonJobItemConfig {

    @Bean
    public AsyncItemProcessListener<?, ?> asyncItemProcessListener() {
        AsyncItemProcessListener<Object, Object> listener = new AsyncItemProcessListener<>();
        listener.setDelegate(new ItemProcessListener<>() {

            private final Logger logger = LoggerFactory.getLogger(getClass());

            @Override
            public void beforeProcess(Object item) {}

            @Override
            public void afterProcess(Object item, Object result) {
                logger.info("Item processed: {} ----> {}", item, result);
            }

            @Override
            public void onProcessError(Object item, Exception e) {
                logger.error("Error processing item: {}", item, e);
            }
        });
        return listener;
    }

    @Bean
    public AsyncItemWriteListener<?> asyncItemWriteListener() {
       var listener = new AsyncItemWriteListener<>();
       listener.setDelegate(new ItemWriteListener<>() {

           private final Logger logger = LoggerFactory.getLogger(getClass());

           @Override
           public void beforeWrite(List<?> items) {}

           @Override
           public void afterWrite(List<?> items) {
                logger.info("Wrote {} items: {}", items.size(), items);
           }

           @Override
           public void onWriteError(Exception exception, List<?> items) {
                logger.error("Error writing items: {}", items);
           }
       });
       return listener;
    }
}
