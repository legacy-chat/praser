package ca.awoo.praser;

import ca.awoo.fwoabl.function.Function;

public final class Util {
    private Util() {
    }

    public static <TToken, TMatchInitial, TMatchFinal> Parser<TToken, TMatchFinal> map(
            final Parser<TToken, TMatchInitial> parser,
            final Function<TMatchInitial, TMatchFinal> function) {
        return new Parser<TToken, TMatchFinal>() {
            public TMatchFinal parse(ParseContext<TToken> context) throws ParseException {
                return function.invoke(parser.parse(context));
            }
        };
    }
}
