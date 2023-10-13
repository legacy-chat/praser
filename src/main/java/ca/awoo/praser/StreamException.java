package ca.awoo.praser;

/**
 * An exception that occurs while reading an {@link InputStreamOf}.
 */
public class StreamException extends Exception{

    /**
     * Creates a new {@link StreamException}.
     * @param message the message
     */
    public StreamException(String message){
        super(message);
    }

    /**
     * Creates a new {@link StreamException}.
     * @param message the message
     * @param cause the cause
     */
    public StreamException(String message, Throwable cause){
        super(message, cause);
    }

    /**
     * Creates a new {@link StreamException}.
     * @param cause the cause
     */
    public StreamException(Throwable cause){
        super(cause);
    }

    /**
     * Creates a new {@link StreamException}.
     */
    public StreamException(){
        super();
    }
}
