package it.vincendep.popcorn.common;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.lang.NonNull;
import org.springframework.util.concurrent.ListenableFuture;

public class AsyncItemProcessListener<I,O> implements ItemProcessListener<I, ListenableFuture<O>> {

    private ItemProcessListener<I, O> delegate;

    public void setDelegate(@NonNull ItemProcessListener<I, O> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void beforeProcess(I item) {
        delegate.beforeProcess(item);
    }

    @Override
    public void afterProcess(I item, ListenableFuture<O> future) {
        if (future != null) {
            future.addCallback(result -> delegate.afterProcess(item, result), ex -> onProcessError(item, (Exception) ex));
        } else {
            delegate.afterProcess(item, null);
        }
    }

    @Override
    public void onProcessError(I item, Exception e) {
        delegate.onProcessError(item, e);
    }
}
