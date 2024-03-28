package ca.awoo.praser;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import ca.awoo.fwoabl.function.Predicate;

public class FilterContextTest {

    private Context<Integer> createContext(Integer... ints){
        return new StreamContext<Integer>(new IteratorStream<Integer>(Arrays.<Integer>asList(ints).iterator()));
    }

    @Test
    public void filterTest() throws Exception {
        Context<Integer> context = createContext(1, 2, 1, 4);
        Context<Integer> filterContext = new FilterContext<Integer>(context, new Predicate<Integer>(){
            public boolean invoke(Integer i){
                return i == 1;
            }
        });
        assertEquals("Read a one", 1, (int)filterContext.next().get());
        assertEquals("Read a one", 1, (int)filterContext.next().get());
        assertTrue("End of stream", filterContext.next().isNone());
    }

    @Test
    public void cloneTest() throws Exception {
        Context<Integer> context = createContext(1, 2, 1, 4);
        Context<Integer> filterContext = new FilterContext<Integer>(context, new Predicate<Integer>(){
            public boolean invoke(Integer i){
                return i == 1;
            }
        });
        Context<Integer> clone = filterContext.clone();
        assertEquals("Read a one from clone", 1, (int)clone.next().get());
        assertEquals("Read a one from clone", 1, (int)clone.next().get());
        assertTrue("End of stream on clone", clone.next().isNone());
        assertEquals("Read a one from original", 1, (int)filterContext.next().get());
        assertEquals("Read a one from original", 1, (int)filterContext.next().get());
        assertTrue("End of stream on original", filterContext.next().isNone());
    }
}
