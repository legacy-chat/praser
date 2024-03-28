package ca.awoo.praser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.OptionalNoneException;
import ca.awoo.fwoabl.function.Function;

import static ca.awoo.praser.Combinators.many;
import static ca.awoo.praser.Combinators.map;
import static ca.awoo.fwoabl.function.Functions.equal;

public final class Text {
    private Text(){}

    public static InputStreamOf<Character> characterStream(final InputStream is, final Charset cs){
        return new InputStreamOf<Character>(){
            @SuppressWarnings("resource")
            private final InputStreamReader reader = new InputStreamReader(is, cs);

            @Override
            protected Optional<Character> readStream() throws StreamException {
                try{
                    int next = reader.read();
                    if(next == -1){
                        return new Optional.None<Character>();
                    }else{
                        return new Optional.Some<Character>((char)next);
                    }
                }catch(Exception e){
                    throw new StreamException("An exception occured while reading the underlying stream", e);
                }
            }

            public void close() throws IOException {
                reader.close();
            }
        };
    }

    public static CharStreamContext contextFromStream(final InputStream is, final Charset cs){
        return new CharStreamContext(characterStream(is, cs));
    }

    public static <Token> Parser<Token, String> stringFold(final Parser<Token, ? extends Collection<Character>> parser){
        return map(parser, new Function<Collection<Character>, String>(){
            public String invoke(Collection<Character> chars){
                StringBuilder sb = new StringBuilder();
                for(Character c : chars){
                    sb.append(c);
                }
                return sb.toString();
            }

            @Override
            public String toString(){
                return "StringFold";
            }
        });
    }

    public static <Token> Parser<Token, String> stringAppend(final Parser<Token, ? extends Collection<String>> parser){
        return map(parser, new Function<Collection<String>, String>(){
            public String invoke(Collection<String> strings){
                StringBuilder sb = new StringBuilder();
                for(String s : strings){
                    sb.append(s);
                }
                return sb.toString();
            }

            @Override
            public String toString(){
                return "StringAppend";
            }
        });
    }

    public static Parser<Character, String> tag(final String tag){
        return new Parser<Character,String>() {
            public String parse(Context<Character> context) throws ParseException {
                for(char c : tag.toCharArray()){
                    try{
                        Optional<Character> next = context.next();
                        if(next.isNone()){
                            throw new ParseException(context, "Unexpected end of stream while reading tag " + tag);
                        }
                        if(!next.test(equal(c))){
                            throw new ParseException(context, "Could not read tag " + tag);
                        }
                    } catch(StreamException e){
                        throw new ParseException(context, "Exception while reading tag " + tag, e);
                    }
                }
                return tag;
            }

            @Override
            public String toString(){
                return "Tag(" + tag + ")";
            }
        };
    }

    public static Parser<Character, Character> oneOf(final String string){
        return new Parser<Character, Character>(){
            public Character parse(Context<Character> context) throws ParseException {
                try {
                    Optional<Character> c = context.next();
                    if(c.isNone()){
                        throw new ParseException(context, "Unexpected end of stream while reading one of " + string);
                    }
                    if(string.indexOf(c.get()) == -1){
                        throw new ParseException(context, "Expected one of " + string + " but got " + c);
                    }
                    return c.get();
                } catch (StreamException e) {
                    throw new ParseException(context, "Exception while reading one of " + string, e);
                } catch(OptionalNoneException e){
                    throw new ParseException(context, "Unreachable", e);
                }
                
            }

            @Override
            public String toString(){
                return "OneOf(" + string + ")";
            }
        };
    }

    /**
     * Converts a parser that returns a character into a parser that returns a string
     * @param <Token> The token type
     * @param parser The parser that returns a character
     * @return A parser that returns a string
     */
    public static <Token> Parser<Token, String> charToString(final Parser<Token, Character> parser){
        return map(parser, new Function<Character, String>(){
            public String invoke(Character c){
                return c.toString();
            }

            @Override
            public String toString(){
                return "CharToString";
            }
        });
    }

    public static Parser<Character, Character> whitespace(){
        return oneOf(" \n\r\t");
    }

    public static Parser<Character, Character> digit(){
        return oneOf("0123456789");
    }

    public static Parser<Character, Character> hexDigit(){
        return oneOf("0123456789abcdefABCDEF");
    }

    public static Parser<Character, Character> letter(){
        return oneOf("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    public static Parser<Character, Integer> integer(){
        return map(stringFold(many(digit())), new Function<String, Integer>(){
            public Integer invoke(String digits){
                return Integer.parseInt(digits);
            }

            @Override
            public String toString(){
                return "ToInteger";
            }
        });
    }


}
