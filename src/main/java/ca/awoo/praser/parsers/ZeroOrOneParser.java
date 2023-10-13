package ca.awoo.praser.parsers;

import ca.awoo.fwoabl.Optional;
import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;

/**
 * A {@link Parser} that matches zero or one of a {@link Parser}.
 */
public class ZeroOrOneParser<TToken, TMatch> extends Parser<TToken, Optional<TMatch>>{

    private Parser<TToken, TMatch> parser;

    /**
     * Creates a new {@link ZeroOrOneParser}.
     * @param parser the parser to match
     */
    public ZeroOrOneParser(Parser<TToken, TMatch> parser) {
        this.parser = parser;
    }

    /**
     * Parses zero or one instances of the parser. If the parser does not match, returns a match with {@link Optional#none(Class)}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Match<Optional<TMatch>> parse(InputStreamOf<TToken> input, int offset) throws ParseException {
        Match<TMatch> match = parser.parse(input, offset);
        if(match.isMatch()){
            return new Match<Optional<TMatch>>(Optional.some(match.value), match.length);
        }else{
            return new Match<Optional<TMatch>>((Optional<TMatch>) Optional.none(Object.class), 0);
        }
    }
    
}
