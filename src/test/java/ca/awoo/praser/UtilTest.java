package ca.awoo.praser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ca.awoo.fwoabl.function.Function;
import ca.awoo.praser.character.StringCharacterStream;
import ca.awoo.praser.character.StringParser;

/**
 * Tests for utility functions on praser.Util.
 */
public class UtilTest {

    @Test
    public void testMap() throws Exception{
        Parser <Character, String> trueStringParser = new StringParser("true");
        Parser <Character, Boolean> trueParser = Util.map(trueStringParser, new Function<String, Boolean>() {
            public Boolean invoke(String value) {
                return Boolean.valueOf(value);
            }
        });
        ParseContext<Character> context = new ParseContext<Character>(new StringCharacterStream("true"));
        assertTrue("Parsed true succesfully", trueParser.parse(context));
    }
}
