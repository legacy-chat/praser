package ca.awoo.praser.character;

import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;

/**
 * A {@link Parser} that matches a single character.
 */
public class SingleCharacterParser implements Parser<Character, Character> {

    private final char character;

    /**
     * Creates a new {@link SingleCharacterParser}.
     * @param character the character to match
     */
    public SingleCharacterParser(char character) {
        this.character = character;
    }

    public Character parse(ParseContext<Character> context) throws ParseException {
        try{
            Character next = context.read();
            if(next == null || next != character){
                throw new ParseException("Expected '" + character + "' but got '" + next + "'");
            }
            return character;
        }catch(StreamException e){
            throw new ParseException("An exception occured while reading the stream", e);
        }
    }
    
}
