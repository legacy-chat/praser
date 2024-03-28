package ca.awoo.praser;

import java.io.IOException;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.function.Predicate;

/**
 * A stream that filters its input using a Predicate.
 * @param <T> the type of object to filter
 * @see InputStreamOf
 */
public class FilterStream<T> extends InputStreamOf<T> {
    private final InputStreamOf<T> input;
    private final Predicate<T> filter;

    public FilterStream(InputStreamOf<T> input, Predicate<T> filter) {
        this.input = input;
        this.filter = filter;
    }

    public void close() throws IOException {
        input.close();
    }

    @Override
    protected Optional<T> readStream() throws StreamException {
        Optional<T> next = input.read();
        while(next.isSome() && !next.test(filter)){
            next = input.read();
        }
        return next;
    }
    
}
