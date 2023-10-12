package ca.awoo.praser;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import org.junit.Test;

/**
 * Unit tests for {@link LookaheadInputStream}.
 */
public class LookaheadInputStreamTest {

    /**
     * Tests {@link LookaheadInputStream#peek()}.
     * <p>
     * Creates a {@link LookaheadInputStream} with a byte array input stream containing the bytes 0x01, 0x02, 0x03, 0x04.
     * Asserts that the first call to {@link LookaheadInputStream#peek()} returns 0x01.
     * Asserts that the first call to {@link LookaheadInputStream#peek(int)} with an offset of 0 returns 0x01, with an offset of 1 returns 0x02, etc.
     * Asserts that the first call to {@link LookaheadInputStream#read()} returns 0x01.
     * Asserts that the second call to {@link LookaheadInputStream#peek()} returns 0x02, now that the 0x01 has been consumed.
     * Asserts that the second call to {@link LookaheadInputStream#peek(int)} with an offset of 0 returns 0x02, with an offset of 1 returns 0x03, etc.
     * Continue to read and assert peeks are correct until the end of the stream is reached.
     * </p>
     * @throws Exception if an exception occures while running the test. This of course indicates a failure.
     */
    @Test
    public void testPeek() throws Exception {
        //Create byte array input stream
        byte[] bytes = new byte[] { 0x01, 0x02, 0x03, 0x04 };
        LookaheadInputStream in = new LookaheadInputStream(new ByteArrayInputStream(bytes));
        assertEquals("in.peek() == 1", 1, in.peek());
        assertEquals("in.peek(0) == 1", 1, in.peek(0));
        assertEquals("in.peek(1) == 2", 2, in.peek(1));
        assertEquals("in.peek(2) == 3", 3, in.peek(2));
        assertEquals("in.peek(3) == 4", 4, in.peek(3));
        assertEquals("in.read() == 1", 1, in.read());
        assertEquals("in.peek() == 2", 2, in.peek());
        assertEquals("in.peek(0) == 2", 2, in.peek(0));
        assertEquals("in.peek(1) == 3", 3, in.peek(1));
        assertEquals("in.peek(2) == 4", 4, in.peek(2));
        assertEquals("in.read() == 2", 2, in.read());
        assertEquals("in.peek() == 3", 3, in.peek());
        assertEquals("in.peek(0) == 3", 3, in.peek(0));
        assertEquals("in.peek(1) == 4", 4, in.peek(1));
        assertEquals("in.read() == 3", 3, in.read());
        assertEquals("in.peek() == 4", 4, in.peek());
        assertEquals("in.peek(0) == 4", 4, in.peek(0));
        assertEquals("in.read() == 4", 4, in.read());
    }

    /**
     * Tests {@link LookaheadInputStream#peek()} with a read before the first peek.
     * Here in case creating the buffer in {@link LookaheadInputStream#read()} is a problem.
     * @throws Exception if an exception occures while running the test. This of course indicates a failure.
     */
    @Test
    public void readBeforePeek() throws Exception {
        byte[] bytes = new byte[] { 0x01, 0x02, 0x03, 0x04 };
        LookaheadInputStream in = new LookaheadInputStream(new ByteArrayInputStream(bytes));
        assertEquals("in.read() == 1", 1, in.read());
        assertEquals("in.peek() == 2", 2, in.peek());
        assertEquals("in.peek(0) == 2", 2, in.peek(0));
        assertEquals("in.peek(1) == 3", 3, in.peek(1));
        assertEquals("in.peek(2) == 4", 4, in.peek(2));
    }

    /**
     * Tests {@link LookaheadInputStream#peek()} with a read beyond the end of the stream.
     * Peeking beyond the end of the stream should return -1.
     * @throws Exception if an exception occures while running the test. This of course indicates a failure.
     */
    @Test
    public void readBeyondEnd() throws Exception {
        byte[] bytes = new byte[] { 0x01, 0x02, 0x03, 0x04 };
        LookaheadInputStream in = new LookaheadInputStream(new ByteArrayInputStream(bytes));
        assertEquals("in.read() == 1", 1, in.read());
        assertEquals("in.read() == 2", 2, in.read());
        assertEquals("in.read() == 3", 3, in.read());
        assertEquals("in.read() == 4", 4, in.read());
        assertEquals("in.peek() == -1", -1, in.peek());
        assertEquals("in.peek(0) == -1", -1, in.peek(0));
        assertEquals("in.peek(1) == -1", -1, in.peek(1));
        assertEquals("in.peek(2) == -1", -1, in.peek(2));
    }

    /**
     * Tests {@link LookaheadInputStream#read(byte[], int, int)} to ensure we didn't break it.
     * @throws Exception if an exception occures while running the test. This of course indicates a failure.
     */
    @Test
    public void readBuffer() throws Exception {
        byte[] bytes = new byte[] { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 };
        LookaheadInputStream in = new LookaheadInputStream(new ByteArrayInputStream(bytes));
        byte[] buffer = new byte[4];
        in.read(buffer, 0, 4);
        assertEquals("buffer[0] == 1", 1, buffer[0]);
        assertEquals("buffer[1] == 2", 2, buffer[1]);
        assertEquals("buffer[2] == 3", 3, buffer[2]);
        assertEquals("buffer[3] == 4", 4, buffer[3]);
        assertEquals("in.peek() == 5", 5, in.peek());
        assertEquals("in.peek(0) == 5", 5, in.peek(0));
        assertEquals("in.peek(1) == 6", 6, in.peek(1));
        assertEquals("in.peek(2) == 7", 7, in.peek(2));
        assertEquals("in.peek(3) == 8", 8, in.peek(3));
        assertEquals("in.read() == 5", 5, in.read());
    }
    
}
