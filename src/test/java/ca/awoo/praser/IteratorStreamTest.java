package ca.awoo.praser;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

/**
 * Unit tests for {@link IteratorStream}.
 */
public class IteratorStreamTest {

    /**
     * Tests {@link IteratorStream#read()} by reading the objects from an {@link Iterator}.
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testIterStream() throws Exception {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        InputStreamOf<Integer> input = new IteratorStream<Integer>(list.iterator());
        for(int i : list){
            assertEquals("input.read() == i", i, (int)input.read().get());
        }
    }

    /**
     * Tests {@link IteratorStream#peek()} by peeking at the objects from an {@link Iterator}.
     * <p>
     * For each object in the {@link Iterator}, asserts that the next object in the stream is the same as the object by peeking, and that the same result is returned by reading.
     * </p>
     * @throws Exception if an exception occures while running the test
     */
    @Test
    public void testPeek() throws Exception {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        InputStreamOf<Integer> input = new IteratorStream<Integer>(list.iterator());
        for(int i : list){
            assertEquals("input.peek() == i", i, (int)input.peek().get());
            assertEquals("input.read() == i", i, (int)input.read().get());
        }
    }
}
