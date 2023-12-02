package ca.awoo.praser.character;

import ca.awoo.praser.ParseContext;
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

    public String parse(ParseContext<Character> context) throws ParseException {
        try{
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < string.length(); i++){
                Character next = context.read();
                if(next == null || next != string.charAt(i)){
                    throw new ParseException("Expected '" + string + "' but got '" + builder.toString() + "'");
                }
                builder.append(next);
            }
            return builder.toString();
        }catch(StreamException e){
            throw new ParseException("An exception occured while reading the stream", e);
        }
    }
    
}
