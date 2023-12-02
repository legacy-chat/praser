package ca.awoo.praser;

import java.util.ArrayList;

/**
 * A read-only stream of objects. Supports arbitrary lookahead.
 */
public abstract class InputStreamOf<T> {
    private ArrayList<T> buffer;

    /**
     * Returns the next object in the stream without consuming it.
     * @return the next object in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    public T peek() throws StreamException {
        if (buffer == null) {
            buffer = new ArrayList<T>();
            if(!buffer.add(readStream())){
                return null;
            }
        }
        if(buffer.isEmpty()){
            if(!buffer.add(readStream())){
                return null;
            }
        }
        return buffer.get(0);
    }

    /**
     * Returns the object at the specified offset in the stream without consuming any objects.
     * @param offset the offset from the current position
     * @return the object at the specified offset in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    public T peek(int offset) throws StreamException {
        if (buffer == null) {
            buffer = new ArrayList<T>(offset);
        }
        while(buffer.size() <= offset){
            buffer.add(readStream());
        }
        return buffer.get(offset);
    }

    /**
     * Returns the next object in the stream and consumes it.
     * @return the next object in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    public T read() throws StreamException {
        if(buffer == null){
            buffer = new ArrayList<T>();
        }
        if(buffer.isEmpty()){
            buffer.add(readStream());
        }
        return buffer.remove(0);
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
            buffer.add(readStream());
        }
        for(int i = 0; i < count; i++){
            buffer.remove(0);
        }
    }

    /**
     * Stream implementation. Used by the rest of the class to read new objects from the stream into the buffer.
     * Seperating this from {@link #read()} allows buffer management to be handled by the rest of the class without the implementer having to worry about it.
     * @return the next object in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    protected abstract T readStream() throws StreamException;
}
