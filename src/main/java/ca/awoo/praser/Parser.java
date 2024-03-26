package ca.awoo.praser;

public interface Parser<TToken, TMatch> {

    public TMatch parse(Context<TToken> context) throws ParseException;
}
