package ca.awoo.praser.parsers;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.util.Collection;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.Parser;
import ca.awoo.praser.character.*;

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
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("aaaaa".getBytes("UTF-8")), "UTF-8");
        Parser<Character, Collection<Character>> parser = new ZeroOrMoreParser<Character, Character>(new SingleCharacterParser('a'));
        ParseContext<Character> context = new ParseContext<Character>(input);
        Collection<Character> match = parser.parse(context);
        assertEquals("Got 5 matches", 5, match.size());
    }

    /**
     * Tests with no matches.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testNone() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("bbbbb".getBytes("UTF-8")), "UTF-8");
        Parser<Character, Collection<Character>> parser = new ZeroOrMoreParser<Character, Character>(new SingleCharacterParser('a'));
        ParseContext<Character> context = new ParseContext<Character>(input);
        Collection<Character> match = parser.parse(context);
        assertEquals("Got 0 matches", 0, match.size());
    }

    /**
     * Tests with matches of more than 1 token each.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void moreTokensThanMatchesTest() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("ababab".getBytes("UTF-8")), "UTF-8");
        Parser<Character, Collection<String>> parser = new ZeroOrMoreParser<Character, String>(new StringParser("ab"));
        ParseContext<Character> context = new ParseContext<Character>(input);
        Collection<String> match = parser.parse(context);
        assertEquals("Got 3 matches", 3, match.size());
    }
    
}
