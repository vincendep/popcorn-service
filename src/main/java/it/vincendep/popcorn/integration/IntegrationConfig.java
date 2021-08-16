package it.vincendep.popcorn.integration;

import it.vincendep.popcorn.PopcornApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@ComponentScan
@EnableScheduling
@RequiredArgsConstructor
@Profile("!" + PopcornApplication.DEV)
public class IntegrationConfig {

    private final Job popcornJob;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0 0 * * ?")
    public void runPopcornJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(popcornJob, new JobParameters());
    }
}
