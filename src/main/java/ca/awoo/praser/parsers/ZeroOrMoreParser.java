package ca.awoo.praser.parsers;

import java.util.ArrayList;
import java.util.Collection;

import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;

/**
 * A parser that matches zero or more instances of another parser.
 */
public class ZeroOrMoreParser<TToken, TMatch> implements Parser<TToken, Collection<TMatch>> {

    private final Parser<TToken, TMatch> parser;

    /**
     * Creates a new {@link ZeroOrMoreParser}.
     * @param parser the parser to match
     */
    public ZeroOrMoreParser(Parser<TToken, TMatch> parser) {
        this.parser = parser;
    }

    public Collection<TMatch> parse(ParseContext<TToken> context) throws ParseException {
        try{
            ArrayList<TMatch> matches = new ArrayList<TMatch>();
            while(true){
                context.push();
                try{
                    TMatch match = parser.parse(context);
                    matches.add(match);
                    context.merge();
                }catch(ParseException e){
                    context.pop();
                    break;
                }
            }
            return matches;
        }catch(StreamException e){
            throw new ParseException("An exception occured while parsing", e);
        }
    }
}
