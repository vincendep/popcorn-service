package it.vincendep.popcorn.common;

import it.vincendep.popcorn.util.MongoUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * <p>
 * A {@link ItemWriter} implementation that writes to a MongoDB store using an implementation of Spring Data's
 * {@link MongoOperations}.  Since MongoDB is not a transactional store, a best effort is made to persist
 * written data at the last moment, yet still honor job status contracts.  No attempt to roll back is made
 * if an error occurs during writing.
 * </p>
 *
 * <p>
 * This writer is thread-safe once all properties are set (normal singleton behavior) so it can be used in multiple
 * concurrent transactions.
 * </p>
 *
 * @author Michael Minella
 * @author Parikshit Dutta
 * @author Mahmoud Ben Hassine
 * @author Vincenzo De Petris
 *
 */
public class MongoItemWriter<T> implements ItemWriter<T>, InitializingBean {

    private static final String ID_KEY = "_id";

    private MongoOperations template;
    private final Object bufferKey;
    private String collection;
    private WriteOperation writeOperation = WriteOperation.UPDATE;
    private Function<T, Query> query;

    public enum WriteOperation {
        UPDATE,
        REPLACE,
        DELETE
    }

    public MongoItemWriter() {
        super();
        this.bufferKey = new Object();
    }

    public void setWriteOperation(@NonNull WriteOperation writeOperation) {
        this.writeOperation = writeOperation;
    }

    public void setQuery(Function<T, Query> upsertQueryGenerator) {
        this.query = upsertQueryGenerator;
    }

    /**
     * Set the {@link MongoOperations} to be used to save items to be written.
     *
     * @param template the template implementation to be used.
     */
    public void setTemplate(MongoOperations template) {
        this.template = template;
    }

    /**
     * Get the {@link MongoOperations} to be used to save items to be written.
     * This can be called by a subclass if necessary.
     *
     * @return template the template implementation to be used.
     */
    protected MongoOperations getTemplate() {
        return template;
    }

    /**
     * Set the name of the Mongo collection to be written to.
     *
     * @param collection the name of the collection.
     */
    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getCollection() {
        return collection;
    }

    /**
     * If a transaction is active, buffer items to be written just before commit.
     * Otherwise write items using the provided template.
     *
     * @see org.springframework.batch.item.ItemWriter#write(List)
     */
    @Override
    public void write(List<? extends T> items) throws Exception {
        if(!transactionActive()) {
            doWrite(items);
            return;
        }

        List<T> bufferedItems = getCurrentBuffer();
        bufferedItems.addAll(items);
    }

    /**
     * Performs the actual write to the store via the template.
     * This can be overridden by a subclass if necessary.
     *
     * @param items the list of items to be persisted.
     */
    protected void doWrite(List<? extends T> items) {
        if (!CollectionUtils.isEmpty(items)) {
            BulkOperations bulkOperations = initBulkOperations(BulkOperations.BulkMode.ORDERED, items.get(0));
            switch (writeOperation) {
                case DELETE:
                    delete(items, bulkOperations);
                    break;
                case UPDATE:
                    saveOrUpdate(items, bulkOperations);
                    break;
                case REPLACE:
                    saveOrReplace(items, bulkOperations);
                    break;
            }
            bulkOperations.execute();
        }
    }

    protected void delete(List<? extends T> items, BulkOperations bulkOperations) {
        MongoConverter mongoConverter = this.template.getConverter();
        for (T item : items) {
            Document document = new Document();
            mongoConverter.write(item, document);
            if (document.get(ID_KEY) != null) {
                bulkOperations.remove(queryById(document));
            }
        }
    }

    protected void saveOrReplace(List<? extends T> items, BulkOperations bulkOperations) {
        MongoConverter mongoConverter = this.template.getConverter();
        FindAndReplaceOptions upsert = new FindAndReplaceOptions().upsert();
        for (T item : items) {
            Document document = new Document();
            mongoConverter.write(item, document);
            Query query = Optional.ofNullable(this.query)
                    .map(queryFunction -> queryFunction.apply(item))
                    .orElseGet(() -> queryById(document));
            bulkOperations.replaceOne(query, document, upsert);
        }
    }

    protected void saveOrUpdate(List<? extends T> items, BulkOperations bulkOperations) {
        MongoConverter mongoConverter = getTemplate().getConverter();
        for (T item : items) {
            Document document = new Document();
            mongoConverter.write(item, document);
            Query query = Optional.ofNullable(this.query)
                            .map(gen -> gen.apply(item))
                            .orElseGet(() -> queryById(document));
            bulkOperations.upsert(query, MongoUtils.updateFrom(document));
        }
    }

    private Query queryById(Document document) {
        Object objectId = document.get(ID_KEY) != null ? document.get(ID_KEY) : new ObjectId();
        return new Query().addCriteria(Criteria.where(ID_KEY).is(objectId));
    }

    private BulkOperations initBulkOperations(BulkOperations.BulkMode bulkMode, Object item) {
        BulkOperations bulkOperations;
        if (StringUtils.hasText(this.collection)) {
            bulkOperations = this.template.bulkOps(bulkMode, this.collection);
        }
        else {
            bulkOperations = this.template.bulkOps(bulkMode, ClassUtils.getUserClass(item));
        }
        return bulkOperations;
    }

    private boolean transactionActive() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @SuppressWarnings("unchecked")
    private List<T> getCurrentBuffer() {
        if(!TransactionSynchronizationManager.hasResource(bufferKey)) {
            TransactionSynchronizationManager.bindResource(bufferKey, new ArrayList<T>());

            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void beforeCommit(boolean readOnly) {
                    List<T> items = (List<T>) TransactionSynchronizationManager.getResource(bufferKey);

                    if(!CollectionUtils.isEmpty(items)) {
                        if(!readOnly) {
                            doWrite(items);
                        }
                    }
                }

                @Override
                public void afterCompletion(int status) {
                    if(TransactionSynchronizationManager.hasResource(bufferKey)) {
                        TransactionSynchronizationManager.unbindResource(bufferKey);
                    }
                }
            });
        }

        return (List<T>) TransactionSynchronizationManager.getResource(bufferKey);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.state(template != null, "A MongoOperations implementation is required.");
    }
}