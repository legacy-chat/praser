package ca.awoo.praser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.function.Function;

public final class Combinators {
    private Combinators() {}

    /**
     * Matches any of the given parsers.
     * @param <Token> the type of tokens
     * @param <Match> the type of matches
     * @param parsers the parsers to try
     * @return a parser that matches any of the given parsers
     */
    public static <Token, Match> Parser<Token, Match> or(final Parser<Token, Match>... parsers) {
        return new Parser<Token,Match>() {
            public Match parse(Context<Token> context) throws ParseException {
                List<ParseException> exceptions = new ArrayList<ParseException>();
                for (Parser<Token, Match> parser : parsers) {
                    try {
                        return parser.parse(context.clone());
                    } catch (ParseException e) {
                        exceptions.add(e);
                    }
                }
                throw new MultiParseException(context, exceptions);
            }
        };
    }

    /**
     * Optional parser returns Optional.None if the parser fails.
     * @param <Token> the type of tokens
     * @param <Match> the type of matches
     * @param parser the parser to try
     * @return a parser that returns Optional.None if the parser fails
     */
    public static <Token, Match> Parser<Token, Optional<Match>> optional(final Parser<Token, Match> parser) {
        return new Parser<Token, Optional<Match>>() {
            @SuppressWarnings("unchecked")
            public Optional<Match> parse(Context<Token> context) throws ParseException {
                try{
                    return Optional.some(parser.parse(context.clone()));
                } catch (ParseException e) {
                    return (Optional<Match>) Optional.none(Object.class);
                }
            }
        };
    }

    /**
     * Create a parser that maps the output of a parser
     * @param <Token> the type of tokens
     * @param <Match1> the type of the output of the parser
     * @param <Match2> the type of the output of the function
     * @param parser the parser to map
     * @param function the function to apply
     * @return a parser that maps the output of a parser
     */
    public static <Token, Match1, Match2> Parser<Token, Match2> map(final Parser<Token, Match1> parser, final Function<Match1, Match2> function) {
        return new Parser<Token, Match2>() {
            public Match2 parse(Context<Token> context) throws ParseException {
                return function.invoke(parser.parse(context));
            }
        };
    }

    /**
     * Match the given parser as many times as possible
     * @param <Token> the type of tokens
     * @param <Match> the type of matches
     * @param parser the parser to match
     * @return A parser that matches the given parser as many times as possible
     */
    public static <Token, Match> Parser<Token, List<Match>> many(final Parser<Token, Match> parser) {
        return new Parser<Token, List<Match>>() {
            public List<Match> parse(Context<Token> context) throws ParseException {
                List<Match> matches = new ArrayList<Match>();
                while(true){
                    try{
                        //We have to clone the context so that we can backtrack if the parser fails
                        Context<Token> clone = context.clone();
                        matches.add(parser.parse(clone));
                        //Success, advance the main context
                        context.skip(clone.getOffset() - context.getOffset());
                    }catch(ParseException e){
                        break;
                    } catch (StreamException e) {
                        throw new ParseException(context, "Exception while reading many", e);
                    }
                }
                return matches;
            }
        };
    }

    /**
     * Match any token
     * @param <Token> the type of tokens
     * @return a parser that matches any token
     */
    public static <Token> Parser<Token, Token> any() {
        return new Parser<Token, Token>() {
            public Token parse(Context<Token> context) throws ParseException {
                try {
                    Token next = context.next();
                    if(next == null){
                        throw new ParseException(context, "Unexpected end of stream");
                    }
                    return next;
                } catch (StreamException e) {
                    throw new ParseException(context, "Exception while reading any", e);
                }
            }
        };
    }

    public static <Token, Matches, Match> Parser<Token, Match> fold(final Parser<Token, ? extends Collection<Matches>> parser, final Function<Collection<Matches>, Match> function) {
        return new Parser<Token, Match>() {
            public Match parse(Context<Token> context) throws ParseException {
                return function.invoke(parser.parse(context));
            }
        };
    }
}
