package ca.awoo.praser.character;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;

/**
 * Tests for {@link SingleCharacterParser}.
 */
public class SingleCharacterParserTest {
    @Test
    public void testParse() throws Exception {
        InputStreamOf<Character> input1 = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes("UTF-8")), "UTF-8");
        InputStreamOf<Character> input2 = new CharacterStream(new ByteArrayInputStream("Goodbye World!".getBytes("UTF-8")), "UTF-8");
        SingleCharacterParser parser = new SingleCharacterParser('H');
        ParseContext<Character> context1 = new ParseContext<Character>(input1);
        ParseContext<Character> context2 = new ParseContext<Character>(input2);

        Character match = parser.parse(context1);
        assertEquals("Matched 'H'", 'H', (char)match);
        try{
            parser.parse(context2);
            fail("Expected ParseException");
        }catch (ParseException e){
            // Expected
        }
    }
}
