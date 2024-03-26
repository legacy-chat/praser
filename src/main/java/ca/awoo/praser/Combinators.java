package ca.awoo.praser;

import java.util.ArrayList;
import java.util.List;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.function.Function;

public class Parsers {
    public static <Token, Match> Parser<Token, Match> or(final Parser<Token, Match>... parsers) {
        return new Parser<Token,Match>() {
            public Match parse(ParseContext<Token> context) throws ParseException {
                List<ParseException> exceptions = new ArrayList<ParseException>();
                for (Parser<Token, Match> parser : parsers) {
                    try {
                        context.push();
                        Match match = parser.parse(context);
                        try {
                            context.merge();
                            return match;
                        } catch (StreamException e) {
                            throw new ParseException(context, "An exception was thrown by the underlying stream!", e);
                        }
                    } catch (ParseException e) {
                        context.pop();
                        exceptions.add(e);
                    }
                }
                throw new MultiParseException(context, exceptions);
            }
        };
    }

    public static <Token, Match> Parser<Token, Optional<Match>> optional(final Parser<Token, Match> parser) {
        return new Parser<Token, Optional<Match>>() {
            @SuppressWarnings("unchecked")
            public Optional<Match> parse(ParseContext<Token> context) throws ParseException {
                try {
                    context.push();
                    Optional<Match> match = Optional.some(parser.parse(context));
                    try {
                        context.merge();
                        return match;
                    } catch (StreamException e) {
                        throw new ParseException(context, "An exception was thrown by the underlying stream!", e);
                    }
                } catch (ParseException e) {
                    return (Optional<Match>) Optional.none(Object.class);
                }
            }
        };
    }

    public static <Token, Match1, Match2> Parser<Token, Match2> map(final Parser<Token, Match1> parser, final Function<Match1, Match2> function) {
        return new Parser<Token, Match2>() {
            public Match2 parse(ParseContext<Token> context) throws ParseException {
                return function.invoke(parser.parse(context));
            }
        };
    }
}
