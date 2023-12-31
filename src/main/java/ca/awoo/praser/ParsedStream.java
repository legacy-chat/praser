package ca.awoo.praser;

/**
 * A stream that parses its input using a {@link Parser}.
 * @param <TToken> the type of token to parse
 * @param <TMatch> the parsed type
 */
public class ParsedStream<TToken, TMatch> extends InputStreamOf<TMatch> {
    private Parser<TToken, TMatch> parser;
    private ParseContext<TToken> context;

    /**
     * Creates a new {@link ParsedStream}.
     * @param parser the parser to use
     * @param input the input to parse
     */
    public ParsedStream(Parser<TToken, TMatch> parser, InputStreamOf<TToken> input) {
        this.parser = parser;
        this.context = new ParseContext<TToken>(input);
    }

    /**
     * Parses the next object in the stream.
     * @return the next parsed object
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    @Override
    protected TMatch readStream() throws StreamException {
        try{
            return parser.parse(context);
        }catch(ParseException e){
            throw new StreamException("An exception occured while parsing", e);
        }
    }
}
