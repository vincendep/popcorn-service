package it.vincendep.popcorn.integration.job.api;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobExplorer explorer;
    private final JobOperator operator;

    public long startJob(String jobName, String parameters) throws JobExecutionException {
        JobInstance jobInstance = explorer.getLastJobInstance(jobName);
        if (jobInstance == null) {
            return operator.start(jobName, parameters);
        }
        return operator.restart(explorer.getLastJobExecution(jobInstance).getId());
    }

    public boolean stopJob(long executionId) throws JobExecutionException {
        return operator.stop(executionId);
    }

    public boolean stopJob(String jobName) throws JobExecutionException {
        Set<JobExecution> executions = explorer.findRunningJobExecutions(jobName);
        boolean result = true;
        for (JobExecution execution: executions) {
            result = operator.stop(execution.getId()) && result;
        }
        return result;
    }
}

