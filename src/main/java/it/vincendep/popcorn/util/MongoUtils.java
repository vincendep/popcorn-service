package it.vincendep.popcorn.util;

import org.bson.Document;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;

public class MongoUtils {

    /**
     * Crete an {@link Update} from the given {@link Document}.
     * @param document
     * @return update for all not null fields
     */
    public static Update updateFrom(@NonNull Document document) {
        Update update = new Update();
        document.forEach((key, value) -> addUpdate(key, value, update));
        return update;
    }

    private static void addUpdate(String key, Object value, Update update) {
        if (value instanceof Document) {
            ((Document) value).forEach((k, v) -> addUpdate(key + "." + k, v, update));
        } else if (value != null){
            update.set(key, value);
        }
    }
}
