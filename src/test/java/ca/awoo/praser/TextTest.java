package ca.awoo.praser;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import org.junit.Test;

public class TextTest {
    @Test
    public void contextFromStreamTest() throws Exception{
        Context<Character> context = Text.contextFromStream(new ByteArrayInputStream("Hello, World!".getBytes("UTF-8")), Charset.forName("UTF-8"));
        assertEquals('H', (char)context.next());
        assertEquals('e', (char)context.next());
        assertEquals('l', (char)context.next());
        assertEquals('l', (char)context.next());
        assertEquals('o', (char)context.next());
        assertEquals(',', (char)context.next());
        assertEquals(' ', (char)context.next());
        assertEquals('W', (char)context.next());
        assertEquals('o', (char)context.next());
        assertEquals('r', (char)context.next());
        assertEquals('l', (char)context.next());
        assertEquals('d', (char)context.next());
        assertEquals('!', (char)context.next());
    }

    @Test
    public void stringFoldTest() throws Exception{
        Parser<Character, String> parser = Text.stringFold(Combinators.many(Combinators.<Character>any()));
        Context<Character> context = new StringContext("Hello, World!");
        assertEquals("Hello, World!", parser.parse(context));
    }

    @Test
    public void tagTest() throws Exception{
        Parser<Character, String> parser = Text.tag("Hello, World!");
        Context<Character> context = new StringContext("Hello, World!");
        assertEquals("Hello, World!", parser.parse(context));
    }

    @Test
    public void tagFailTest() throws Exception{
        Parser<Character, String> parser = Text.tag("Hello, World!");
        Context<Character> context = new StringContext("Hello, World");
        try{
            parser.parse(context);
            fail("ParseException expected");
        }catch(ParseException e){
        }
    }

    @Test
    public void oneOfTest() throws Exception{
        Parser<Character, Character> parser = Text.oneOf("Helo, Wrd!");
        Context<Character> context = new StringContext("Hello, World!");
        assertEquals('H', (char)parser.parse(context));
        assertEquals('e', (char)parser.parse(context));
        assertEquals('l', (char)parser.parse(context));
        assertEquals('l', (char)parser.parse(context));
        assertEquals('o', (char)parser.parse(context));
        assertEquals(',', (char)parser.parse(context));
        assertEquals(' ', (char)parser.parse(context));
        assertEquals('W', (char)parser.parse(context));
        assertEquals('o', (char)parser.parse(context));
        assertEquals('r', (char)parser.parse(context));
        assertEquals('l', (char)parser.parse(context));
        assertEquals('d', (char)parser.parse(context));
        assertEquals('!', (char)parser.parse(context));
    }

    @Test
    public void oneOfFailTest() throws Exception{
        Parser<Character, Character> parser = Text.oneOf("elo Wrd!");
        Context<Character> context = new StringContext("Hello, World!");
        try{
            parser.parse(context);
            fail("ParseException expected");
        }catch(ParseException e){
        }
    }

    @Test
    public void whitespaceTest() throws Exception{
        Parser<Character, Character> parser = Text.whitespace();
        Context<Character> context = new StringContext(" \n\r\tHello, World!");
        assertEquals(' ', (char)parser.parse(context));
        assertEquals('\n', (char)parser.parse(context));
        assertEquals('\r', (char)parser.parse(context));
        assertEquals('\t', (char)parser.parse(context));
    }

    @Test
    public void whitespaceFailTest() throws Exception{
        Parser<Character, Character> parser = Text.whitespace();
        Context<Character> context = new StringContext("Hello, World!");
        try{
            parser.parse(context);
            fail("ParseException expected");
        }catch(ParseException e){
        }
    }

    @Test
    public void digitTest() throws Exception{
        Parser<Character, Character> parser = Text.digit();
        Context<Character> context = new StringContext("0123456789");
        for(int i = 0; i < 10; i++){
            assertEquals((char)('0' + i), (char)parser.parse(context));
        }
    }

    @Test
    public void digitFailTest() throws Exception{
        Parser<Character, Character> parser = Text.digit();
        Context<Character> context = new StringContext("Hello, World!");
        try{
            parser.parse(context);
            fail("ParseException expected");
        }catch(ParseException e){
        }
    }

    @Test
    public void letterTest() throws Exception{
        Parser<Character, Character> parser = Text.letter();
        Context<Character> context = new StringContext("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        for(int i = 0; i < 26; i++){
            assertEquals((char)('a' + i), (char)parser.parse(context));
        }
        for(int i = 0; i < 26; i++){
            assertEquals((char)('A' + i), (char)parser.parse(context));
        }
    }

    @Test
    public void letterFailTest() throws Exception{
        Parser<Character, Character> parser = Text.letter();
        Context<Character> context = new StringContext("!@#$%^&*()_+");
        try{
            parser.parse(context);
            fail("ParseException expected");
        }catch(ParseException e){
        }
    }

    @Test
    public void integerTest() throws Exception{
        Parser<Character, Integer> parser = Text.integer();
        Context<Character> context = new StringContext("1234567890");
        assertEquals(1234567890, (int)parser.parse(context));
    }
}
