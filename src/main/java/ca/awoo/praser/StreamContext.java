package ca.awoo.praser;

import ca.awoo.fwoabl.Optional;

public class StreamContext<T> implements Context<T> {
    private final InputStreamOf<T> input;

    private long offset;

    public StreamContext(InputStreamOf<T> input) {
        this.input = input;
        offset = 0;
    }

    private StreamContext(InputStreamOf<T> input, long offset) {
        this.input = input;
        this.offset = offset;
    }

    public Optional<T> next() throws StreamException {
        offset++;
        return input.read();
    }

    public StreamContext<T> clone() {
        return new StreamContext<T>(new PeekingInputStream<T>(input), offset);
    }

    public void skip(long n) throws StreamException {
        for (long i = 0; i < n; i++) {
            next();
        }
    }

    public long getOffset() {
        return offset;
    }
}
