package ca.awoo.praser;


/**
 * An exception that occurs while parsing.
 */
public class ParseException extends Exception {

    private final Context<?> context;

    /**
     * Creates a new {@link ParseException}.
     * @param message the message
     */
    public ParseException(Context<?> context, String message) {
        super(message + " at " + context.toString());
        this.context = context;
    }

    /**
     * Creates a new {@link ParseException}.
     * @param message the message
     * @param cause the cause
     */
    public ParseException(Context<?> context, String message, Throwable cause) {
        super(message + " at " + context.toString(), cause);
        this.context = context;
    }

    /**
     * Creates a new {@link ParseException}.
     * @param cause the cause
     */
    public ParseException(Context<?> context, Throwable cause){
        super("at " + context.toString(), cause);
        this.context = context;
    }

    /**
     * Creates a new {@link ParseException}.
     */
    public ParseException(Context<?> context) {
        super("at " + context.toString());
        this.context = context;
    }

    /**
     * Gets the context.
     * @return the context
     */
    public Context<?> getContext() {
        return context;
    }
}
