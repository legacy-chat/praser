package ca.awoo.praser.character;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;

/**
 * Unit tests for {@link CharacterStream}.
 */
public class CharacterStreamTest {

    /**
     * Tests {@link CharacterStream#read()} by reading the characters from a string.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testCharStream() throws Exception {
        String s = "Hello World!";
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream(s.getBytes()));
        for(char c : s.toCharArray()){
            assertEquals("input.read() == c", c, (char)input.read());
        }
    }

    /**
     * Tests {@link CharacterStream#peek()} by peeking at the characters from a string.
     * <p>
     * For each character in the string, asserts that the next character in the stream is the same as the character by peeking, and that the same result is returned by reading.
     * </p>
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testPeek() throws Exception {
        String s = "Hello World!";
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream(s.getBytes()));
        for(char c : s.toCharArray()){
            assertEquals("input.peek() == c", c, (char)input.peek());
            assertEquals("input.read() == c", c, (char)input.read());
        }
    }
}
