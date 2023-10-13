package ca.awoo.praser.parsers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.Parser;
import ca.awoo.praser.Parser.Match;
import ca.awoo.praser.character.CharacterStream;
import ca.awoo.praser.character.StringParser;

public class OrParserTest {

    /**
     * Tests {@link OrParser#parse(InputStreamOf)}.
     * <p>
     * Creates a {@link OrParser} with a {@link StringParser} for "Hello" and a {@link StringParser} for "World".
     * Creates a {@link CharacterStream} with the string "Hello World!".
     * Asserts that the {@link OrParser} matches the {@link CharacterStream}.
     * Asserts that the {@link Match} value is "Hello".
     * Asserts that the {@link Match} length is 5.
     * Creates a {@link CharacterStream} with the string "World Hello!".
     * Asserts that the {@link OrParser} matches the {@link CharacterStream}.
     * Asserts that the {@link Match} value is "World".
     * Asserts that the {@link Match} length is 5.
     * </p>
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testOr() throws Exception {
        InputStreamOf<Character> input1 = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes()));
        InputStreamOf<Character> input2 = new CharacterStream(new ByteArrayInputStream("World Hello!".getBytes()));
        Parser<Character, String> parser1 = new StringParser("Hello");
        Parser<Character, String> parser2 = new StringParser("World");
        @SuppressWarnings("unchecked")
        Parser<Character, String> parser = new OrParser<Character, String>(parser1, parser2);

        Match<String> match = parser.parse(input1);
        assertTrue("Match on \"Hello World!\"", match.isMatch());
        assertEquals("Matched \"Hello\"", "Hello", match.value);
        assertEquals("Matched 5 characters", 5, match.length);

        match = parser.parse(input2);
        assertTrue("Match on \"World Hello!\"", match.isMatch());
        assertEquals("Matched \"World\"", "World", match.value);
        assertEquals("Matched 5 characters", 5, match.length);

    }
}
