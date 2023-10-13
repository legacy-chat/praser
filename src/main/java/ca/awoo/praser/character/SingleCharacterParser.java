package ca.awoo.praser.character;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;

/**
 * A {@link Parser} that matches a single character.
 */
public class SingleCharacterParser extends Parser<Character, Character> {

    private final char character;

    /**
     * Creates a new {@link SingleCharacterParser}.
     * @param character the character to match
     */
    public SingleCharacterParser(char character) {
        this.character = character;
    }

    public Match<Character> parse(InputStreamOf<Character> input, int offset) throws ParseException {
        try{
            Character next = input.peek(offset);
            if(next == null || next != character){
                return new Match<Character>(null, 0);
            }
            return new Match<Character>(character, 1);
        }catch(StreamException e){
            throw new ParseException("An exception occured while reading the stream", e);
        }
    }
    
}
