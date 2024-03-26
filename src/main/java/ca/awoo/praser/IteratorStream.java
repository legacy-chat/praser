package ca.awoo.praser;

import java.io.IOException;
import java.util.Iterator;

/**
 * An {@link InputStreamOf} that reads objects from an {@link Iterator}.
 * @param <T> the type of object to read
 */
public class IteratorStream<T> extends InputStreamOf<T> {

    private Iterator<T> iterator;

    /**
     * Creates a new {@link IteratorStream}.
     * @param iterator the iterator to read from
     */
    public IteratorStream(Iterator<T> iterator) {
        this.iterator = iterator;
    }

    /**
     * Reads the next object from the underlying iterator.
     * @return the next object in the iterator
     */
    @Override
    protected T readStream() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }

    public void close() throws IOException {
    }
    
}
