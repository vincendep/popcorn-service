package it.vincendep.popcorn.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public class AsyncItemWriteListener<T> implements ItemWriteListener<ListenableFuture<T>> {

    private ItemWriteListener<T> delegate;

    public void setDelegate(ItemWriteListener<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void beforeWrite(List<? extends ListenableFuture<T>> items) {
        try {
            this.delegate.beforeWrite(unwrapItems(items));
        } catch (Exception ignored) {}
    }

    @Override
    public void afterWrite(List<? extends ListenableFuture<T>> items) {
        try {
            this.delegate.afterWrite(unwrapItems(items));
        } catch (Exception ignored) {}
    }

    @Override
    public void onWriteError(Exception exception, List<? extends ListenableFuture<T>> items) {
        try {
            this.delegate.onWriteError(exception, unwrapItems(items));
        } catch (Exception ignored) {}
    }

    private List<T> unwrapItems(List<? extends ListenableFuture<T>> items) throws Exception {
        List<T> list = new ArrayList<>();
        for (Future<T> future : items) {
            try {
                T item = future.get();
                if (item != null) {
                    list.add(item);
                }
            } catch (ExecutionException | InterruptedException var7) {
                Throwable cause = var7.getCause();
                if (cause instanceof Exception) {
                    log.debug("An exception was thrown while processing an item", cause);
                }
                throw (Exception) cause;
            }
        }
        return list;
    }
}
