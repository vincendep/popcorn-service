package it.vincendep.popcorn.common;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

public class ResourceCleaner implements Consumer<Closeable> {

    @Override
    public void accept(Closeable closeable) {
        try {
           closeable.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
