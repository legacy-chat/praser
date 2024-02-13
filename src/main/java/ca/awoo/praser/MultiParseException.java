package ca.awoo.praser;

import ca.awoo.praser.parsers.OrParser;

/**
 * An exception that is thrown when multiple parsers fail to parse the same input.
 * @see ParseException
 * @see OrParser
 */
public class MultiParseException extends ParseException{
    /**
     * The causes of the exception.
     */
    public final Throwable[] causes;

    private static int getThrowableDepth(Throwable t){
        int depth = 0;
        while(t != null){
            depth++;
            t = t.getCause();
        }
        return depth;
    }

    private static Throwable getDeepesThrowable(Throwable... causes){
        Throwable deepest = causes[0];
        for(Throwable cause : causes){
            if(getThrowableDepth(cause) > getThrowableDepth(deepest)){
                deepest = cause;
            }
        }
        return deepest;
    }

    /**
     * Creates a new {@link MultiParseException}.
     * @param message the message to use
     * @param causes the causes of the exception
     */
    public MultiParseException(String message, Throwable... causes){
        super(message, getDeepesThrowable(causes));
        this.causes = causes;
    }
}
