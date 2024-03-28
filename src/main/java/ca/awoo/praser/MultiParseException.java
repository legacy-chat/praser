package ca.awoo.praser;

import java.util.List;

public class MultiParseException extends ParseException {
    private final List<ParseException> exceptions;

    public MultiParseException(Context<?> context, List<ParseException> exceptions) {
        super(context, "Multiple parse exceptions", getDeepestThrowable(exceptions));
        this.exceptions = exceptions;
    }

    public List<ParseException> getExceptions() {
        return exceptions;
    }

    private static int getThrowableDepth(Throwable t){
        int depth = 0;
        while(t != null){
            depth++;
            t = t.getCause();
        }
        return depth;
    }

    private static Throwable getDeepestThrowable(List<? extends Throwable> causes){
        Throwable deepest = causes.get(0);
        for(Throwable cause : causes){
            if(getThrowableDepth(cause) > getThrowableDepth(deepest)){
                deepest = cause;
            }
        }
        return deepest;
    }
}
