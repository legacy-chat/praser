package ca.awoo.praser.character;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.ParseContext;
import ca.awoo.praser.ParseException;

/**
 * Tests for {@link CharInStringParser}.
 */
public class CharInStringParserTest {
    
    @Test
    public void testCharInStringParser() throws Exception {
        InputStreamOf<Character> input = new CharacterStream(new ByteArrayInputStream("Hello World!".getBytes("UTF-8")), "UTF-8");
        CharInStringParser parser = new CharInStringParser("oleH");
        ParseContext<Character> context = new ParseContext<Character>(input);
        for(int i = 0; i < 5; i++){
            parser.parse(context);
        }
        for(int i = 5; i < 7; i++){
            try{
                parser.parse(context);
                fail("Expected ParseException");
            }catch (ParseException e){
                // Expected
            }
        }
    }
}
