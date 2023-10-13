package ca.awoo.praser.character;

import java.util.Collection;

import ca.awoo.praser.InputStreamOf;
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
public class WhitespaceParser extends Parser<Character, String> {

    private final Parser<Character, Collection<Character>> parser;

    @SuppressWarnings("unchecked")
    public WhitespaceParser() {
        Parser<Character, Character> singleParser = new OrParser<Character,Character>(
            new SingleCharacterParser(' '),
            new SingleCharacterParser('\t'),
            new SingleCharacterParser('\n'),
            new SingleCharacterParser('\r')
        );
        parser = new ZeroOrMoreParser<Character, Character>(singleParser);
    }

    @Override
    public Match<String> parse(InputStreamOf<Character> input, int offset) throws ParseException {
        Match<Collection<Character>> match = parser.parse(input, offset);
        if(match.isMatch()){
            StringBuilder builder = new StringBuilder();
            for(Character character : match.value){
                builder.append(character);
            }
            return new Match<String>(builder.toString(), match.length);
        }
        return new Match<String>(null, 0);
    }
    
}
