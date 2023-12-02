package ca.awoo.praser.character;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.StreamException;

public class StringCharacterStream extends InputStreamOf<Character> {

    private String input;
    private int index;

    public StringCharacterStream(String input){
        this.input = input;
        this.index = 0;
    }

    @Override
    protected Character readStream() throws StreamException {
        if(index >= input.length()){
            return null;
        }else{
            return input.charAt(index++);
        }
    }
    
}
