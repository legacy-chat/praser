package ca.awoo.praser.character;

import java.util.Collection;

import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.parsers.OrParser;
import ca.awoo.praser.parsers.ZeroOrMoreParser;

/**
 * A parser that matches zero or more whitespace characters.
 * <p>
 * Whitespace characters are defined as:
 * <ul>
 * <li>space</li>
 * <li>tab</li>
 * <li>newline</li>
 * <li>carriage return</li>
 * </ul>
 */
public class WhitespaceParser implements Parser<Character, String> {

    private final Parser<Character, Collection<Character>> parser;

    @SuppressWarnings("unchecked")
    public WhitespaceParser() {
        Parser<Character, Character> singleParser = new OrParser<Character,Character>(
            new SingleCharacterParser(' '),
            new SingleCharacterParser('\t'),
            new SingleCharacterParser('\n'),
            new SingleCharacterParser('\r'),
            new SingleCharacterParser('\f')
        );
        parser = new ZeroOrMoreParser<Character, Character>(singleParser);
    }

    public String parse(ParseContext<Character> context) throws ParseException {
        Collection<Character> chars =  parser.parse(context);
        StringBuilder builder = new StringBuilder();
        for(Character character : chars){
            builder.append(character);
        }
        return builder.toString();
    }
    
}
