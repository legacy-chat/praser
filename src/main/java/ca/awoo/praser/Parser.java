package ca.awoo.praser;

public interface Parser<TToken, TMatch> {

    public TMatch parse(ParseContext<TToken> context) throws ParseException;
}
