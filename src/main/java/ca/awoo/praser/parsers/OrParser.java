package ca.awoo.praser.parsers;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;

/**
 * A parser that tries to parse its input using a list of parsers, in order.
 * @param <TToken> the type of token to parse
 * @param <TMatch> the parsed type
 */
public class OrParser<TToken, TMatch> implements Parser<TToken, TMatch> {

    private Parser<TToken, TMatch>[] parsers;

    /**
     * Creates a new {@link OrParser}.
     * @param parsers the parsers to try, in order
     */
    public OrParser(Parser<TToken, TMatch> ...parsers) {
        this.parsers = parsers;
    }

    /**
     * Parses the next object in the stream.
     * Uses each parser in order until one succeeds.
     * If none succeed, returns a failed match.
     * @return the next parsed object
     * @throws ParseException if an exception occurs while parsing the input
     */
    public Match<TMatch> parse(InputStreamOf<TToken> input) throws ParseException {
        for (Parser<TToken, TMatch> parser : parsers) {
            Match<TMatch> match = parser.parse(input);
            if (match.isMatch()) {
                return match;
            }
        }
        return new Match<TMatch>(null, 0);
    }
    
}
