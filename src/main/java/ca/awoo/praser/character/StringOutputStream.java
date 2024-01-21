package ca.awoo.praser.character;

import ca.awoo.praser.OutputStreamOf;
import ca.awoo.praser.StreamException;

/**
 * An {@link OutputStreamOf} that writes characters to a string.
 * @see StringOutputStream#getString()
 */
public class StringOutputStream extends OutputStreamOf<Character> {

    private final StringBuilder builder;

    /**
     * Creates a new {@link StringOutputStream}.
     */
    public StringOutputStream(){
        this.builder = new StringBuilder();
    }

    /**
     * Returns the string that has been written to this stream.
     * @return the string that has been written to this stream
     */
    public String getString(){
        return builder.toString();
    }

    /**
     * Writes a character to the string.
     */
    @Override
    public void write(Character value) throws StreamException {
        builder.append(value);
    }
    
}
