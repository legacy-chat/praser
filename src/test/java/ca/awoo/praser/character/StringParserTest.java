package ca.awoo.praser.character;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.Parser;

/**
 * Unit tests for {@link StringParser}.
 */
public class StringParserTest {

    @Test
    public void testHelloWorld() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes("UTF-8")), "UTF-8");
        Parser<Character, String> parser = new StringParser("Hello World!");
        ParseContext<Character> context = new ParseContext<Character>(input);
        String match = parser.parse(context);
        assertEquals("Matched \"Hello World!\"", "Hello World!", match);
    }
    
}
