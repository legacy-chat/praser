package ca.awoo.praser.character;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.parsers.OrParser;

/**
 * A {@link Parser} that matches any character in a string.
 */
public class CharInStringParser extends Parser<Character, Character> {

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

    @Override
    public Match<Character> parse(InputStreamOf<Character> input, int offset) throws ParseException {
        return parser.parse(input, offset);
    }
    
}
