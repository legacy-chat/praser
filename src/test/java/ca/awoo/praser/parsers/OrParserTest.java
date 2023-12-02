package ca.awoo.praser.parsers;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.Parser;
import ca.awoo.praser.character.CharacterStream;
import ca.awoo.praser.character.StringParser;

public class OrParserTest {

    @Test
    public void testOr() throws Exception {
        InputStreamOf<Character> input1 = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes("UTF-8")), "UTF-8");
        InputStreamOf<Character> input2 = new CharacterStream(new ByteArrayInputStream("World Hello!".getBytes("UTF-8")), "UTF-8");
        Parser<Character, String> parser1 = new StringParser("Hello");
        Parser<Character, String> parser2 = new StringParser("World");
        ParseContext<Character> context1 = new ParseContext<Character>(input1);
        ParseContext<Character> context2 = new ParseContext<Character>(input2);
        @SuppressWarnings("unchecked")
        Parser<Character, String> parser = new OrParser<Character, String>(parser1, parser2);

        String match = parser.parse(context1);
        assertEquals("Matched \"Hello\"", "Hello", match);

        match = parser.parse(context2);
        assertEquals("Matched \"World\"", "World", match);

    }
}
