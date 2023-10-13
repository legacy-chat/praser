package ca.awoo.praser.character;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.Parser.Match;

/**
 * Tests for {@link CharInStringParser}.
 */
public class CharInStringParserTest {
    
    /**
     * Tests {@link CharInStringParser#parse(InputStreamOf, int)}.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testCharInStringParser() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes()));
        CharInStringParser parser = new CharInStringParser("oleH");
        for(int i = 0; i < 5; i++){
            Match<Character> match = parser.parse(input, i);
            assertTrue("Matching letters of hello", match.isMatch());
            assertEquals("Matched 1", 1, match.length);
        }
        for(int i = 5; i < 7; i++){
            Match<Character> match = parser.parse(input, i);
            assertFalse("Not matching letters of \" W\"", match.isMatch());
        }
        for(int i = 7; i < 8; i++){
            Match<Character> match = parser.parse(input, i);
            assertTrue("Matching letters of \"o\"", match.isMatch());
            assertEquals("Matched 1", 1, match.length);
        }
        for(int i = 8; i < 9; i++){
            Match<Character> match = parser.parse(input, i);
            assertFalse("Not matching letters of \"r\"", match.isMatch());
        }
        for(int i = 9; i < 10; i++){
            Match<Character> match = parser.parse(input, i);
            assertTrue("Matching letters of \"l\"", match.isMatch());
            assertEquals("Matched 1", 1, match.length);
        }
        for(int i = 10; i < 11; i++){
            Match<Character> match = parser.parse(input, i);
            assertFalse("Not matching letters of \"d!\"", match.isMatch());
        }

    }
}
