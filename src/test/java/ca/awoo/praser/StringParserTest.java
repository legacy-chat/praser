package ca.awoo.praser;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.Parser.Match;
import ca.awoo.praser.character.CharacterStream;
import ca.awoo.praser.character.StringParser;

/**
 * Unit tests for {@link StringParser}.
 */
public class StringParserTest {

    /**
     * Tests {@link StringParser#parse(InputStreamOf)}.
     * <p>
     * Creates a {@link StringParser} with the string "Hello World!".
     * Creates a {@link CharacterStream} with the string "Hello World!".
     * Asserts that the {@link StringParser} matches the {@link CharacterStream}.
     * Asserts that the {@link Match} value is "Hello World!".
     * Asserts that the {@link Match} length is 12.
     * </p>
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testHelloWorld() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes()));
        Parser<Character, String> parser = new StringParser("Hello World!");
        Match<String> match = parser.parse(input);
        assertEquals("Matched \"Hello World!\"", "Hello World!", match.value);
        assertEquals("Matched 12 characters", 12, match.length);
    }
    
}
