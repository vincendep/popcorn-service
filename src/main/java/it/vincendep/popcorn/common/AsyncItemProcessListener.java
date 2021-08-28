package it.vincendep.popcorn.common;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.lang.NonNull;
import org.springframework.util.concurrent.ListenableFuture;

public class AsyncItemProcessListener<I,O> implements ItemProcessListener<I, ListenableFuture<O>> {

    private ItemProcessListener<I, O> delegate;

    public void setDelegate(@NonNull ItemProcessListener<I, O> delegate) {
        this.delegate = delegate;
    }

    @Override
    @BeforeProcess
    public void beforeProcess(I item) {
        delegate.beforeProcess(item);
    }

    @Override
    @AfterProcess
    public void afterProcess(I item, ListenableFuture<O> future) {
        if (future != null) {
            future.addCallback(result -> delegate.afterProcess(item, result), ex -> onProcessError(item, (Exception) ex));
        } else {
            delegate.afterProcess(item, null);
        }
    }

    @Override
    @OnProcessError
    public void onProcessError(I item, Exception e) {
        delegate.onProcessError(item, e);
    }
}
