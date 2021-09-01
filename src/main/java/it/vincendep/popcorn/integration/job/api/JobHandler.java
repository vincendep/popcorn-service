package it.vincendep.popcorn.integration.job.api;


import it.vincendep.popcorn.integration.job.common.JobUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JobHandler {

    private final JobService service;

    public Mono<ServerResponse> runJob(ServerRequest request) {
        try {
            String jobName = request.pathVariable("jobName");
            String jobParameters = JobUtils.toJobParameterString(request.queryParams().toSingleValueMap());
            long jobInstanceId = service.startJob(jobName, jobParameters);
            return ServerResponse.ok().bodyValue(jobInstanceId);
        } catch (JobExecutionException | UnexpectedJobExecutionException e) {
            return ServerResponse.badRequest().bodyValue(e.getMessage());
        }
    }

    public Mono<ServerResponse> stopJob(ServerRequest request) {
        try {
            String jobName = request.pathVariable("jobName");
            boolean stopSignalSent =service.stopJob(jobName);
            return ServerResponse.ok().bodyValue(stopSignalSent);
        } catch (JobExecutionException | UnexpectedJobExecutionException ex) {
            return ServerResponse.badRequest().bodyValue(ex.getMessage());
        }
    }
}
