package ca.awoo.praser;

import ca.awoo.fwoabl.Optional;

public class StringContext implements Context<Character>{
    private final String input;
    private int offset = 0;
    private int line = 1;
    private int column = 1;

    public StringContext(String input) {
        this.input = input;
    }

    public StringContext(String input, int offset, int line, int column) {
        this.input = input;
        this.offset = offset;
        this.line = line;
        this.column = column;
    }

    public Optional<Character> next() throws StreamException {
        if(offset >= input.length()){
            return new Optional.None<Character>();
        }
        Character value = input.charAt(offset);
        if(value == '\n'){
            line++;
            column = 1;
        }else{
            column++;
        }
        offset++;
        return new Optional.Some<Character>(value);
    }

    public StringContext clone() {
        return new StringContext(input, offset, line, column);
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
