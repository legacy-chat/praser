package ca.awoo.praser.parsers;

import ca.awoo.fwoabl.Optional;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;
import ca.awoo.praser.Parser;

/**
 * A {@link Parser} that matches zero or one of a {@link Parser}.
 */
public class ZeroOrOneParser<TToken, TMatch> implements Parser<TToken, Optional<TMatch>>{

    private Parser<TToken, TMatch> parser;

    /**
     * Creates a new {@link ZeroOrOneParser}.
     * @param parser the parser to match
     */
    public ZeroOrOneParser(Parser<TToken, TMatch> parser) {
        this.parser = parser;
    }

    @SuppressWarnings("unchecked")
    public Optional<TMatch> parse(ParseContext<TToken> context) throws ParseException {
        try{
            context.push();
            try{
                TMatch match = parser.parse(context);
                context.merge();
                return Optional.some(match);
            }catch(ParseException e){
                context.pop();
                return (Optional<TMatch>) Optional.none(Object.class);
            }
        }catch(Exception e){
            throw new ParseException("An exception occured while parsing", e);
        }
    }
    
}
