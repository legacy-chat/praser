package ca.awoo.praser.parsers;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.fwoabl.Optional;
import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.Parser;
import ca.awoo.praser.Parser.Match;
import ca.awoo.praser.character.CharacterStream;
import ca.awoo.praser.character.StringParser;

/**
 * Unit tests for {@link ZeroOrOneParser}.
 */
public class ZeroOrOneParserTest {
    /**
     * Tests {@link ZeroOrOneParser#parse(InputStreamOf)}.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testZeroOrOne() throws Exception {
        InputStreamOf<Character> input1 = new CharacterStream(new ByteArrayInputStream("Hello world!".getBytes()));
        InputStreamOf<Character> input2 = new CharacterStream(new ByteArrayInputStream("Goodbye world!".getBytes()));
        Parser<Character, String> parser1 = new StringParser("Hello");
        Parser<Character, Optional<String>> parser = new ZeroOrOneParser<Character,String>(parser1);
        Match<Optional<String>> match = parser.parse(input1);
        assertTrue("Match on \"Hello world!\"", match.isMatch());
        assertTrue("Matched value", match.value.isSome());
        assertEquals("Matched \"Hello\"", "Hello", match.value.get());
        assertEquals("Matched 5 characters", 5, match.length);
        match = parser.parse(input2);
        assertTrue("Match on \"Goodbye world!\"", match.isMatch());
        assertTrue("Matched none", match.value.isNone());
        assertEquals("Matched 0 characters", 0, match.length);
    }
    
}
