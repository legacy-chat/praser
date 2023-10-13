package ca.awoo.praser.character;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;

/**
 * A {@link Parser} that matches an exact string.
 */
public class StringParser implements Parser<Character, String> {

    private String string;

    /**
     * Creates a new {@link StringParser}.
     * @param string the string to match
     */
    public StringParser(String string) {
        this.string = string;
    }

    /**
     * Parses the next match from the input.
     * @return the next match
     */
    public Match<String> parse(InputStreamOf<Character> input) throws ParseException {
        try{
            for(int i = 0; i < string.length(); i++){
                Character next = input.peek(i);
                if(next == null || next != string.charAt(i)){
                    return new Match<String>(null, 0);
                }
            }
            return new Match<String>(string, string.length());
        }catch(StreamException e){
            throw new ParseException("An exception occured while reading the stream", e);
        }
    }
    
}
