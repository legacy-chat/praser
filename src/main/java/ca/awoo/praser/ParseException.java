package ca.awoo.praser;

/**
 * An exception that occurs while parsing.
 */
public class ParseException extends Exception {

    /**
     * Creates a new {@link ParseException}.
     * @param message the message
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Creates a new {@link ParseException}.
     * @param message the message
     * @param cause the cause
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new {@link ParseException}.
     * @param cause the cause
     */
    public ParseException(Throwable cause){
        super(cause);
    }

    /**
     * Creates a new {@link ParseException}.
     */
    public ParseException() {
        super();
    }
}
