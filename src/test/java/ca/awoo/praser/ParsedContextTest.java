package ca.awoo.praser;

import static ca.awoo.praser.Combinators.*;
import static ca.awoo.praser.Text.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ParsedContextTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        Context<Character> context = new StringContext("12 23 34 45 56");
        Context<String> parsedContext = new ParsedContext<Character, String>(context, or(stringFold(oneOrMore(digit())), stringFold(oneOrMore(whitespace()))));
        assertEquals("Parsed 12", "12", parsedContext.next().get());
        assertEquals("Parsed space", " ", parsedContext.next().get());
        assertEquals("Parsed 23", "23", parsedContext.next().get());
        assertEquals("Parsed space", " ", parsedContext.next().get());
        assertEquals("Parsed 34", "34", parsedContext.next().get());
        assertEquals("Parsed space", " ", parsedContext.next().get());
        assertEquals("Parsed 45", "45", parsedContext.next().get());
        assertEquals("Parsed space", " ", parsedContext.next().get());
        assertEquals("Parsed 56", "56", parsedContext.next().get());
        
    }
}
