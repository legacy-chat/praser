package ca.awoo.praser;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.function.Consumer;

public class CharStreamContext implements Context<Character> {

    private final InputStreamOf<Character> input;
    private int offset = 0;
    private int line = 1;
    private int column = 1;

    public CharStreamContext(InputStreamOf<Character> input) {
        this.input = input;
    }

    public CharStreamContext(InputStreamOf<Character> input, int offset, int line, int column) {
        this.input = input;
        this.offset = offset;
        this.line = line;
        this.column = column;
    }

    public Optional<Character> next() throws StreamException {
        Optional<Character> value = input.read();
        value.consume(new Consumer<Character>() {
            public void invoke(Character c) {
                if(c == '\n'){
                    line++;
                    column = 1;
                }else{
                    column++;
                }
                offset++;
            }
        });
        return value;
    }

    public CharStreamContext clone() {
        return new CharStreamContext(new PeekingInputStream<Character>(input), offset, line, column);
    }

    public void skip(long n) throws StreamException {
        for (long i = 0; i < n; i++) {
            next();
        }
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "L" + line + "C" + column + "O" + offset;
    }
    
}
