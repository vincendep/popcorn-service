package it.vincendep.popcorn.common;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class AsyncItemProcessor<I, O> implements ItemProcessor<I, ListenableFuture<O>>, InitializingBean {
    private ItemProcessor<I, O> delegate;
    private TaskExecutor taskExecutor = new SyncTaskExecutor();

    public AsyncItemProcessor() {
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.delegate, "The delegate must be set.");
    }

    public void setDelegate(ItemProcessor<I, O> delegate) {
        this.delegate = delegate;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Nullable
    public ListenableFuture<O> process(final I item) throws Exception {
        final StepExecution stepExecution = this.getStepExecution();
        ListenableFutureTask<O> task = new ListenableFutureTask<>(() -> {
            if (stepExecution != null) {
                StepSynchronizationManager.register(stepExecution);
            }
            O var1;
            try {
                var1 = AsyncItemProcessor.this.delegate.process(item);
            } finally {
                if (stepExecution != null) {
                    StepSynchronizationManager.close();
                }

            }
            return var1;
        });
        this.taskExecutor.execute(task);
        return task;
    }

    private StepExecution getStepExecution() {
        StepContext context = StepSynchronizationManager.getContext();
        if (context == null) {
            return null;
        } else {
            StepExecution stepExecution = context.getStepExecution();
            return stepExecution;
        }
    }
}
