package it.vincendep.popcorn.integration.batch;

import it.vincendep.popcorn.common.Module;
import lombok.SneakyThrows;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Module
@EnableBatchProcessing
public class BatchModule extends DefaultBatchConfigurer {

    @Override
    @SneakyThrows
    public JobRepository getJobRepository() {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(getTransactionManager());
        return factory.getObject();
    }
}
