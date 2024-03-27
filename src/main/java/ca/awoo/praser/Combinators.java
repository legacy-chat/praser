package ca.awoo.praser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.OptionalNoneException;
import ca.awoo.fwoabl.function.BiFunction;
import ca.awoo.fwoabl.function.Function;

import static ca.awoo.fwoabl.function.Functions.equal;

public final class Combinators {
    private Combinators() {}

    /**
     * Match a single token
     * @param <Token> the type of tokens
     * @param token the token to match
     * @return a parser that matches a single token
     */
    public static <Token> Parser<Token, Token> one(final Token token){
        return new Parser<Token, Token>() {
            public Token parse(Context<Token> context) throws ParseException {
                try {
                    Optional<Token> next = context.next();
                    if(next.isNone()){
                        throw new ParseException(context, "Unexpected end of stream");
                    }
                    if(next.test(equal(token))){
                        return next.get();
                    }else{
                        throw new ParseException(context, "Expected " + token + " but got " + next);
                    }
                } catch (StreamException e) {
                    throw new ParseException(context, "Exception while reading one", e);
                } catch(OptionalNoneException e){
                    throw new ParseException(context, "Unreachable", e);
                }
                
            }
        };
    }

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
                        Context<Token> clone = context.clone();
                        Match match = parser.parse(clone);
                        context.skip(clone.getOffset() - context.getOffset());
                        return match;
                    } catch (ParseException e) {
                        exceptions.add(e);
                    } catch(StreamException e) {
                        throw new ParseException(context, "Exception while reading or", e);
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
            public Optional<Match> parse(Context<Token> context) throws ParseException {
                try{
                    Context<Token> clone = context.clone();
                    Match match = parser.parse(clone);
                    context.skip(clone.getOffset() - context.getOffset());
                    return (Optional<Match>) new Optional.Some<Match>(match);
                } catch (ParseException e) {
                    return (Optional<Match>) new Optional.None<Match>();
                } catch(StreamException e) {
                    throw new ParseException(context, "Exception while reading optional", e);
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
                    Optional<Token> next = context.next();
                    if(next.isNone()){
                        throw new ParseException(context, "Unexpected end of stream");
                    }
                    return next.get();
                } catch (StreamException e) {
                    throw new ParseException(context, "Exception while reading any", e);
                } catch(OptionalNoneException e){
                    throw new ParseException(context, "Unreachable", e);
                }
            }
        };
    }

    /**
     * Fold the output of a parser into a single value
     * @param <Token> the type of tokens
     * @param <Match> the type of matches
     * @param parser the parser to fold
     * @param init the initial value
     * @param function the function to fold with
     * @return a parser that folds the output of a parser into a single value
     */
    public static <Token, Match> Parser<Token, Match> fold(final Parser<Token, ? extends Collection<Match>> parser, final Match init, final BiFunction<Match, Match, Match> function) {
        return new Parser<Token, Match>() {
            public Match parse(Context<Token> context) throws ParseException {
                Match result = init;
                for(Match match : parser.parse(context)){
                    result = function.invoke(result, match);
                }
                return result;
            }
        };
    }
}
