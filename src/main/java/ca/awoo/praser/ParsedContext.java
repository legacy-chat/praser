package ca.awoo.praser;

import ca.awoo.fwoabl.Optional;

/**
 * A context that parses a stream of tokens into a stream of matches.
 * @param <Token> The type of the tokens in the stream.
 * @param <Match> The type of the matches produced by the parser.
 */
public class ParsedContext<Token, Match> implements Context<Match> {
    private final Context<Token> parentContext;
    private final Parser<Token, Match> parser;
    private int offset = 0;

    public ParsedContext(Context<Token> parentContext, Parser<Token, Match> parser) {
        this.parentContext = parentContext;
        this.parser = parser;
    }

    private ParsedContext(Context<Token> parentContext, Parser<Token, Match> parser, int offset) {
        this.parentContext = parentContext;
        this.parser = parser;
        this.offset = offset;
    }

    public Optional<Match> next() throws StreamException {
        offset++;
        try {
            return new Optional.Some<Match>(parser.parse(parentContext));
        } catch (ParseException e) {
            throw new StreamException("An exception occured while parsing", e);
        }
    }
    public void skip(long n) throws StreamException {
        for (long i = 0; i < n; i++) {
            next();
        }
    }
    public long getOffset() {
        return offset;
    }

    public ParsedContext<Token, Match> clone(){
        return new ParsedContext<Token, Match>(parentContext.clone(), parser, offset);
    }

    @Override
    public String toString(){
        return "Parsing at offset " + offset + " in " + parentContext.toString() + " using " + parser.toString();
    }

}
