package ca.awoo.praser;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.function.Predicate;

/**
 * A context that filters a stream of tokens.
 * @param <T> The type of the tokens in the stream.
 */
public class FilterContext<T> implements Context<T> {
    private final Context<T> parentContext;
    private final Predicate<T> filter;
    private long offset = 0;

    public FilterContext(Context<T> parentContext, Predicate<T> filter) {
        this.parentContext = parentContext;
        this.filter = filter;
    }

    private FilterContext(Context<T> parentContext, Predicate<T> filter, long offset) {
        this.parentContext = parentContext;
        this.filter = filter;
        this.offset = offset;
    }

    public Optional<T> next() throws StreamException {
        Optional<T> next = parentContext.next();
        while (next.isSome() && !next.test(filter)) {
            next = parentContext.next();
        }
        offset++;
        return next;
    }

    public void skip(long n) throws StreamException {
        for (long i = 0; i < n; i++) {
            next();
        }
    }

    public long getOffset() {
        return offset;
    }

    public FilterContext<T> clone(){
        return new FilterContext<T>(parentContext.clone(), filter, offset);
    }
    
    @Override
    public String toString(){
        return "FilterContext(" + parentContext.toString() + ", " + filter.toString() + ")@" + offset;
    }
}
