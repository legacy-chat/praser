package ca.awoo.praser.character;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.Parser.Match;

/**
 * Tests for {@link SingleCharacterParser}.
 */
public class SingleCharacterParserTest {
    /**
     * Tests {@link SingleCharacterParser#parse(InputStreamOf)}.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testParse() throws Exception {
        InputStreamOf<Character> input1 = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes()));
        InputStreamOf<Character> input2 = new CharacterStream(new ByteArrayInputStream("Goodbye World!".getBytes()));
        SingleCharacterParser parser = new SingleCharacterParser('H');
        Match<Character> match = parser.parse(input1);
        assertTrue("Match on \"Hello World!\"", match.isMatch());
        assertEquals("Matched 'H'", 'H', (char)match.value);
        assertEquals("Matched 1 character", 1, match.length);
        match = parser.parse(input2);
        assertFalse("No match on \"Goodbye World!\"", match.isMatch());
        assertEquals("Matched 0 characters", 0, match.length);
    }
}
