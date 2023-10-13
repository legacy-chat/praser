package ca.awoo.praser.character;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.Parser.Match;

/**
 * Tests for {@link WhitespaceParser}.
 */
public class WhitespaceParserTest {
    /**
     * Tests {@link WhitespaceParser#parse(InputStreamOf)}.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testParse() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream(" \t\n\r\f".getBytes()));
        WhitespaceParser parser = new WhitespaceParser();
        Match<String> match = parser.parse(input);
        assertTrue("Matched", match.isMatch());
        assertEquals("Matched 5 characters", 5, match.length);
    }
}
