package ca.awoo.praser;

import java.util.List;

public class MultiParseException extends ParseException {
    private final List<ParseException> exceptions;

    public MultiParseException(ParseContext<?> context, List<ParseException> exceptions) {
        super(context, "Multiple parse exceptions");
        this.exceptions = exceptions;
    }

    public List<ParseException> getExceptions() {
        return exceptions;
    }
}
