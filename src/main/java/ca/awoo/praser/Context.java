package ca.awoo.praser;

/**
 * A context for parsing.
 */
public interface Context<T> {
    /**
     * Read the next token, consuming it.
     * @return the token
     * @throws StreamException if there is an error reading the stream
     */
    public T next() throws StreamException;

    /**
     * Skip n tokens.
     * @param n the number of tokens to skip
     * @throws StreamException if there is an error reading the stream
     */
    public void skip(long n) throws StreamException;

    /**
     * Get the current offset in the stream.
     * @return the current offset
     */
    public long getOffset();

    /**
     * Clone the context.
     * @return a clone of the context, calls to next on the clone should not affect the original
     */
    public Context<T> clone();
}
