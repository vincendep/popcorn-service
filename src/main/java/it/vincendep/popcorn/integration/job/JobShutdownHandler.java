package it.vincendep.popcorn.integration.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobShutdownHandler {

    private final JobExplorer jobExplorer;
    private final JobOperator jobOperator;

    @PreDestroy
    public void handleShutdown() throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        // TODO
    }
}
