package ca.awoo.praser.character;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;

/**
 * Tests for {@link WhitespaceParser}.
 */
public class WhitespaceParserTest {

    @Test
    public void testParse() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream(" \t\n\r\f".getBytes("UTF-8")), "UTF-8");
        WhitespaceParser parser = new WhitespaceParser();
        ParseContext<Character> context = new ParseContext<Character>(input);
        String match = parser.parse(context);
        assertEquals("Matched 5 characters", 5, match.length());
    }
}
