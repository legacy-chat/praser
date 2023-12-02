package ca.awoo.praser.parsers;

import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;
import ca.awoo.praser.StreamException;

/**
 * A parser that tries to parse its input using a list of parsers, in order.
 * @param <TToken> the type of token to parse
 * @param <TMatch> the parsed type
 */
public class OrParser<TToken, TMatch> implements Parser<TToken, TMatch> {

    private Parser<TToken, TMatch>[] parsers;
    private String exceptionMessage = "No parser matched";

    /**
     * Creates a new {@link OrParser}.
     * @param parsers the parsers to try, in order
     */
    public OrParser(Parser<TToken, TMatch> ...parsers) {
        this.parsers = parsers;
    }

    /**
     * Creates a new {@link OrParser}.
     * @param exceptionMessage the message to use in the exception thrown if no parser matches
     * @param parsers the parsers to try, in order
     */
    public OrParser(String exceptionMessage, Parser<TToken, TMatch> ...parsers) {
        this.parsers = parsers;
        this.exceptionMessage = exceptionMessage;
    }

    public TMatch parse(ParseContext<TToken> context) throws ParseException {
        try{
            for (Parser<TToken, TMatch> parser : parsers) {
                context.push();
                try{
                    TMatch match = parser.parse(context);
                    context.merge();
                    return match;
                }catch (ParseException e){
                    context.pop();
                }
            }
            throw new ParseException(exceptionMessage);
        }catch(StreamException e){
            throw new ParseException("An exception occured while parsing", e);
        }
    }
    
}
