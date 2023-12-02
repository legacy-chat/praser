package ca.awoo.praser.character;

import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.parsers.OrParser;

/**
 * A {@link Parser} that matches any character in a string.
 */
public class CharInStringParser implements Parser<Character, Character> {

    private Parser<Character, Character> parser;

    /**
     * Creates a new {@link CharInStringParser}.
     * @param string the string to match characters from
     */
    @SuppressWarnings("unchecked")
    public CharInStringParser(String string) {
        Parser<Character, Character>[] parsers = new Parser[string.length()];
        for(int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            parsers[i] = new SingleCharacterParser(c);
        }
        this.parser = new OrParser<Character,Character>(
            parsers
        );
    }

    public Character parse(ParseContext<Character> context) throws ParseException {
        return parser.parse(context);
    }
    
}
