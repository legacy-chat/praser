package ca.awoo.praser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.util.Collection;

import org.junit.Test;

import ca.awoo.praser.Parser.Match;
import ca.awoo.praser.character.*;
import ca.awoo.praser.parsers.*;

/**
 * Unit tests for {@link ZeroOrMoreParser}.
 */
public class ZeroOrMoreParserTest {

    /**
     * Tests with multiple matches.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testMore() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("aaaaa".getBytes()));
        Parser<Character, Collection<Character>> parser = new ZeroOrMoreParser<Character, Character>(new SingleCharacterParser('a'));
        Match<Collection<Character>> match = parser.parse(input);
        assertTrue("Did match", match.isMatch());
        assertEquals("Matched 5 characters", 5, match.length);
        assertEquals("Got 5 matches", 5, match.value.size());
    }

    /**
     * Tests with no matches.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testNone() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("bbbbb".getBytes()));
        Parser<Character, Collection<Character>> parser = new ZeroOrMoreParser<Character, Character>(new SingleCharacterParser('a'));
        Match<Collection<Character>> match = parser.parse(input);
        assertTrue("Did match", match.isMatch());
        assertEquals("Matched 0 characters", 0, match.length);
        assertEquals("Got 0 matches", 0, match.value.size());
    }

    /**
     * Tests with matches of more than 1 token each.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void moreTokensThanMatchesTest() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("ababab".getBytes()));
        Parser<Character, Collection<String>> parser = new ZeroOrMoreParser<Character, String>(new StringParser("ab"));
        Match<Collection<String>> match = parser.parse(input);
        assertTrue("Did match", match.isMatch());
        assertEquals("Matched 6 characters", 6, match.length);
        assertEquals("Got 3 matches", 3, match.value.size());
    }
    
}
