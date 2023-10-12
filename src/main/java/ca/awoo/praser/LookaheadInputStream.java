package ca.awoo.praser;

import java.io.IOException;
import java.io.InputStream;

/**
 * An {@link InputStream} wrapper that supports arbitrary lookahead.
 */
public class LookaheadInputStream extends InputStream {

    private InputStream in;
    //TODO: probably use ArrayStream instead, it has better management of the buffer but boxes the contents
    private int[] buffer;
    private int bufferIndex;

    public LookaheadInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Returns the next byte in the stream without consuming it.
     * If you attempt to read past the end of the stream, this method will return -1.
     * @return the next byte in the stream
     * @throws IOException if an I/O error occurs while reading the underlying stream
     */
    public int peek() throws IOException {
        if (buffer == null) {
            bufferIndex = 0;
            buffer = new int[2];
            buffer[0] = in.read();
            buffer[1] = in.read();
        }
        return buffer[bufferIndex];
    }

    /**
     * Returns the byte at the specified offset in the stream without consuming any bytes.
     * If you attempt to read past the end of the stream, this method will return -1.
     * @param offset the offset from the current position
     * @return the byte at the specified offset in the stream
     * @throws IOException if an I/O error occurs while reading the underlying stream
     */
    public int peek(int offset) throws IOException {
        int bufferPosition = bufferIndex + offset;
        if (buffer == null) {
            bufferIndex = 0;
            buffer = new int[offset];
            for (int i = 0; i < offset; i++) {
                buffer[i] = in.read();
            }
        }
        if (bufferPosition >= buffer.length) {
            int[] newBuffer = new int[offset + 1];
            for (int i = bufferIndex; i < buffer.length; i++) {
                newBuffer[i - bufferIndex] = buffer[i];
            }
            for (int i = buffer.length; i < newBuffer.length; i++) {
                newBuffer[i] = in.read();
            }
            buffer = newBuffer;
            bufferIndex = 0;
            bufferPosition = offset;
        }
        return buffer[bufferPosition];
    }

    /**
     * Consumes and returns the next byte in the stream.
     * @return the next byte in the stream
     * @throws IOException if an I/O error occurs while reading the underlying stream
     * @see InputStream#read()
     */
    public int read() throws IOException {
        if (buffer == null) {
            return in.read();
        }
        int result = buffer[bufferIndex];
        bufferIndex++;
        if (bufferIndex == buffer.length) {
            bufferIndex = 0;
            buffer = new int[2];
            buffer[0] = in.read();
            buffer[1] = in.read();
        }
        return result;
    }
    
}
