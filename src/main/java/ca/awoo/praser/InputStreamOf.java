package ca.awoo.praser;

import java.io.Closeable;
import java.util.ArrayList;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.function.Consumer;

/**
 * A read-only stream of objects. Supports arbitrary lookahead.
 */
public abstract class InputStreamOf<T> implements Closeable{
    private ArrayList<T> buffer;

    private final Consumer<T> bufferConsumer = new Consumer<T>() {
        public void invoke(T value) {
            buffer.add(value);
        }
    };

    /**
     * Returns the next object in the stream without consuming it.
     * @return the next object in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    public Optional<T> peek() throws StreamException {
        if (buffer == null) {
            buffer = new ArrayList<T>();
        }
        if(buffer.isEmpty()){
            Optional<T> next = readStream();
            if(!next.isSome()){
                return new Optional.None<T>();
            }
            next.consume(bufferConsumer);

        }
        return new Optional.Some<T>(buffer.get(0));
    }

    /**
     * Returns the object at the specified offset in the stream without consuming any objects.
     * @param offset the offset from the current position
     * @return the object at the specified offset in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    public Optional<T> peek(int offset) throws StreamException {
        if (buffer == null) {
            buffer = new ArrayList<T>(offset);
        }
        while(buffer.size() <= offset){
            Optional<T> next = readStream();
            if(!next.isSome()){
                return new Optional.None<T>();
            }
            next.consume(bufferConsumer);
        }
        return new Optional.Some<T>(buffer.get(offset));
    }

    /**
     * Returns the next object in the stream and consumes it.
     * @return the next object in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    public Optional<T> read() throws StreamException {
        if(buffer == null){
            buffer = new ArrayList<T>();
        }
        if(buffer.isEmpty()){
            Optional<T> next = readStream();
            if(!next.isSome()){
                return new Optional.None<T>();
            }
            next.consume(bufferConsumer);
        }
        return new Optional.Some<T>(buffer.remove(0));
    }

    /**
     * Consumes a number of objects from the stream without returning them.
     * @param count the number of objects to consume
     * @throws StreamException
     */
    public void consume(int count) throws StreamException {
        if(buffer == null){
            buffer = new ArrayList<T>();
        }
        while(buffer.size() < count){
            readStream().consume(bufferConsumer);
        }
        for(int i = 0; i < count; i++){
            if(buffer.isEmpty())
                break;
            buffer.remove(0);
        }
    }

    /**
     * Stream implementation. Used by the rest of the class to read new objects from the stream into the buffer.
     * Seperating this from {@link #read()} allows buffer management to be handled by the rest of the class without the implementer having to worry about it.
     * @return the next object in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    protected abstract Optional<T> readStream() throws StreamException;
}
