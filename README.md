# praser
A Java 5 stream parsing library.

## Usage
The praser library revolves mainly around the [`Parser`](src/main/java/ca/awoo/praser/Parser.java) interface. All parsers implement this interface. If you use a newer version of Java which supports it, it can be used as a functional interface for lambda expressions. Upon successfully parsing a value, a parser returns the value. If the parser fails, it throws a ParserException. Passed into the `parse` function is a [`ParseContext`](src/main/java/ca/awoo/praser/ParseContext.java) which is used to read tokens from the stream. If you're parser has multiple options, you can call `push` on the `ParseContext` to push it's status onto an internal stack, surround a call to another parser with a try/catch block, and pop the context if it fails. If it succeeds, you should call `ParseContext.merge()` to get rid of the extra stack frame while maintaining your position in the stream. As an example, here is the source for the [`OrParser`](src/main/java/ca/awoo/praser/parsers/OrParser.java):
```java
public class OrParser<TToken, TMatch> implements Parser<TToken, TMatch> {

    private Parser<TToken, TMatch>[] parsers;
    //Descriptive exception messages are important for parsers where exceptions may happen very deep in a tree
    //We allow custom messages so users can create more meaningful exceptions
    private String exceptionMessage = "No parser matched";

    /**
     * Creates a new {@link OrParser}.
     * @param parsers the parsers to try, in order
     */
    public OrParser(Parser<TToken, TMatch> ...parsers) {
        this.parsers = parsers;
    }

    /**
     * Creates a new {@link OrParser}.
     * @param exceptionMessage the message to use in the exception thrown if no parser matches
     * @param parsers the parsers to try, in order
     */
    public OrParser(String exceptionMessage, Parser<TToken, TMatch> ...parsers) {
        this.parsers = parsers;
        this.exceptionMessage = exceptionMessage;
    }

    //This is the parsing function
    public TMatch parse(ParseContext<TToken> context) throws ParseException {
        //The outer try/catch is in case something goes wrong with the underlying stream. It is important that we only throw ParseException from a parser for the try/catch pattern used further in to work.
        try{
            for (Parser<TToken, TMatch> parser : parsers) {
                //Before we try to parse, we need to save our place in the stream
                context.push();
                try{
                    //Try to parse with our parser
                    TMatch match = parser.parse(context);
                    //We only make it this far if parsing succeeded, thus we need to merge to clear the extra frame without losing our place
                    context.merge();
                    //And then return our match
                    return match;
                }catch (ParseException e){
                    //The parser failed, reset back to our saved spot before we try the next one
                    context.pop();
                }
            }
            //We've gone through every parser without a match, throw an exception with our custom message
            throw new ParseException(exceptionMessage);
        }catch(StreamException e){
            throw new ParseException("An exception occured while parsing", e);
        }
    }
}
```