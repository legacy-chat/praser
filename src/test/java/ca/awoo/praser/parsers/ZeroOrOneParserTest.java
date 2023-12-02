package ca.awoo.praser.parsers;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.fwoabl.Optional;
import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.Parser;
import ca.awoo.praser.character.CharacterStream;
import ca.awoo.praser.character.StringParser;

/**
 * Unit tests for {@link ZeroOrOneParser}.
 */
public class ZeroOrOneParserTest {

    @Test
    public void testZeroOrOne() throws Exception {
        InputStreamOf<Character> input1 = new CharacterStream(new ByteArrayInputStream("Hello world!".getBytes("UTF-8")), "UTF-8");
        InputStreamOf<Character> input2 = new CharacterStream(new ByteArrayInputStream("Goodbye world!".getBytes("UTF-8")), "UTF-8");
        Parser<Character, String> parser1 = new StringParser("Hello");
        Parser<Character, Optional<String>> parser = new ZeroOrOneParser<Character,String>(parser1);
        ParseContext<Character> context1 = new ParseContext<Character>(input1);
        ParseContext<Character> context2 = new ParseContext<Character>(input2);
        Optional<String> match = parser.parse(context1);
        assertTrue("Matched value", match.isSome());
        assertEquals("Matched \"Hello\"", "Hello", match.get());

        match = parser.parse(context2);
        assertTrue("Matched none", match.isNone());
    }
    
}
