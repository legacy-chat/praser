package ca.awoo.praser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import ca.awoo.fwoabl.Optional;
import ca.awoo.fwoabl.function.BiFunction;
import ca.awoo.fwoabl.function.Function;

public class CombinatorsTest {

    private Context<Integer> createContext(Integer... ints){
        return new StreamContext<Integer>(new IteratorStream<Integer>(Arrays.<Integer>asList(ints).iterator()));
    }

    @Test
    public void oneTest() throws Exception{
        Context<Integer> context = createContext(1, 2, 3);
        assertEquals((Integer)1, Combinators.one(1).parse(context));
        assertEquals((Integer)2, Combinators.one(2).parse(context));
        assertEquals((Integer)3, Combinators.one(3).parse(context));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void orTest() throws Exception{
        Context<Integer> context = createContext(1, 2, 3, 4);
        Parser<Integer, Integer> parser = Combinators.or(Combinators.one(1), Combinators.one(2), Combinators.one(3));
        assertEquals((Integer)1, parser.parse(context));
        assertEquals((Integer)2, parser.parse(context));
        assertEquals((Integer)3, parser.parse(context));
        try{
            parser.parse(context);
            fail("Expected exception");
        }catch(ParseException e){

        }
    }

    @Test
    public void optionalTest() throws Exception{
        Context<Integer> context = createContext(1, 2, 3);
        Parser<Integer, Optional<Integer>> parser = Combinators.optional(Combinators.one(1));
        assertEquals(new Optional.Some<Integer>(1), parser.parse(context));
        assertEquals(new Optional.None<Integer>(), parser.parse(context));
    }

    @Test
    public void mapTest() throws Exception{
        Context<Integer> context = createContext(1, 2, 3);
        Parser<Integer, String> parser = Combinators.map(Combinators.one(1), new Function<Integer, String>(){
            public String invoke(Integer i){
                return i.toString();
            }
        });
        assertEquals("1", parser.parse(context));
    }

    @Test
    public void manyTest() throws Exception{
        Context<Integer> context = createContext(1, 1, 1, 2, 3, 4, 5);
        Parser<Integer, List<Integer>> parser = Combinators.many(Combinators.one(1));
        List<Integer> result = parser.parse(context);
        assertEquals(3, result.size());
        assertEquals((Integer)1, result.get(0));
        assertEquals((Integer)1, result.get(1));
        assertEquals((Integer)1, result.get(2));
    }

    @Test
    public void anyTest() throws Exception{
        Context<Integer> context = createContext(1, 2, 3);
        Parser<Integer, Integer> parser = Combinators.any();
        assertEquals((Integer)1, parser.parse(context));
        assertEquals((Integer)2, parser.parse(context));
        assertEquals((Integer)3, parser.parse(context));
    }

    @Test
    public void foldTest() throws Exception{
        Context<Integer> context = createContext(1, 2, 3);
        Parser<Integer, Integer> parser = Combinators.fold(Combinators.many(Combinators.<Integer>any()), 0, new BiFunction<Integer, Integer, Integer>(){
            public Integer invoke(Integer a, Integer b){
                return a + b;
            }
        });
        assertEquals((Integer)6, parser.parse(context));
    }
}
