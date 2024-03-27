package ca.awoo.praser;

import java.io.IOException;
import java.util.Iterator;

import ca.awoo.fwoabl.Optional;

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
    protected Optional<T> readStream() {
        if (iterator.hasNext()) {
            return new Optional.Some<T>(iterator.next());
        } else {
            return new Optional.None<T>();
        }
    }

    public void close() throws IOException {
    }
    
}
