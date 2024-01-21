package ca.awoo.praser;

/**
 * A write-only stream of objects.
 */
public abstract class OutputStreamOf<T> {
    /**
     * Writes an object to the stream.
     * @param value the object to write
     * @throws StreamException if an exception occurs while writing to the underlying stream
     */
    public abstract void write(T value) throws StreamException;
}
