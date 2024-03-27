package ca.awoo.praser;

import java.io.IOException;

import ca.awoo.fwoabl.Optional;

/**
 * An input stream that works by peeking at an underlying stream.
 */
public class PeekingInputStream<T> extends InputStreamOf<T> {

    private final InputStreamOf<T> input;
    private int offset = 0;

    public PeekingInputStream(InputStreamOf<T> input) {
        this.input = input;
    }

    @Override
    protected Optional<T> readStream() throws StreamException {
        return input.peek(offset++);
    }

    public void close() throws IOException {
        //Don't close the underlying stream as the whole point of this is that it's still being used somewhere else
    }
    
}
