package it.vincendep.popcorn.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

@Slf4j
public class AsyncItemWriter<T> implements ItemStreamWriter<Future<T>>, InitializingBean {

    private ItemWriter<T> delegate;

    public AsyncItemWriter() {}

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.delegate, "A delegate ItemWriter must be provided.");
    }

    public void setDelegate(ItemWriter<T> delegate) {
        this.delegate = delegate;
    }

    public void write(List<? extends Future<T>> items) throws Exception {
        List<T> list = new ArrayList<>();
        for (Future<T> future : items) {
            try {
                T item = future.get();
                if (item != null) {
                    list.add(future.get());
                }
            } catch (ExecutionException var7) {
                Throwable cause = var7.getCause();
                if (cause != null && cause instanceof Exception) {
                    log.debug("An exception was thrown while processing an item", cause);
                }
                return;
            }
        }
        this.delegate.write(list);
    }

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (this.delegate instanceof ItemStream) {
            ((ItemStream) this.delegate).open(executionContext);
        }
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        if (this.delegate instanceof ItemStream) {
            ((ItemStream) this.delegate).update(executionContext);
        }
    }

    public void close() throws ItemStreamException {
        if (this.delegate instanceof ItemStream) {
            ((ItemStream) this.delegate).close();
        }
    }
}
