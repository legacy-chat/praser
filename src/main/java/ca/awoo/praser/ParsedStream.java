package ca.awoo.praser;

import java.io.IOException;

import ca.awoo.fwoabl.Optional;

/**
 * A stream that parses its input using a {@link Parser}.
 * @param <TToken> the type of token to parse
 * @param <TMatch> the parsed type
 */
public class ParsedStream<TToken, TMatch> extends InputStreamOf<TMatch> {
    private Parser<TToken, TMatch> parser;
    private Context<TToken> context;
    private InputStreamOf<TToken> input;

    /**
     * Creates a new {@link ParsedStream}.
     * @param parser the parser to use
     * @param input the input to parse
     */
    public ParsedStream(Parser<TToken, TMatch> parser, Context<TToken> context) {
        this.parser = parser;
        this.context = context;
    }

    /**
     * Parses the next object in the stream.
     * @return the next parsed object
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    @Override
    protected Optional<TMatch> readStream() throws StreamException {
        try{
            return new Optional.Some<TMatch>(parser.parse(context));
        }catch(ParseException e){
            throw new StreamException("An exception occured while parsing", e);
        }
    }

    public void close() throws IOException {
        input.close();
    }
}
