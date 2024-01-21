package ca.awoo.praser;

import org.junit.Test;

/**
 * Unit tests for {@link MultiParseException}.
 */
public class MultiParseExceptionTest {
    /**
     * Tests {@link MultiParseException#MultiParseException(String, Throwable...)}.
     * <p>
     * Asserts that the message and cause are set correctly. The cause should be the deepest cause passed to the constructor.
     * </p>
     * @throws Exception if an exception occurs while running the test
     */
    @Test
    public void testConstructor() {
        Exception shallow = new Exception();
        Exception deep = new Exception(shallow);
        MultiParseException exception = new MultiParseException("message", shallow, deep);
        assert(exception.getMessage().equals("message"));
        assert(exception.getCause() == deep);
    }
}
