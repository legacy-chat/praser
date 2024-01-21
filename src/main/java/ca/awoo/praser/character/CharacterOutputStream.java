package ca.awoo.praser.character;

import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import ca.awoo.praser.OutputStreamOf;
import ca.awoo.praser.StreamException;

/**
 * An {@link OutputStreamOf} that writes characters to an {@link OutputStream}.
 */
public class CharacterOutputStream extends OutputStreamOf<Character> {

    private final OutputStream output;
    private final Charset charset;

    /**
     * Creates a new {@link CharacterOutputStream}.
     * @param output the underlying stream to write to
     * @param charset the character set to use when writing to the underlying stream
     */
    public CharacterOutputStream(OutputStream output, Charset charset){
        this.output = output;
        this.charset = charset;
    }

    /**
     * Writes a character to the underlying stream.
     */
    @Override
    public void write(Character value) throws StreamException {
        try{
            ByteBuffer bytes = charset.newEncoder().encode(java.nio.CharBuffer.wrap(new char[]{value}));
            output.write(bytes.array(), 0, bytes.limit());
        }catch(Exception e){
            throw new StreamException("An exception occured while writing to the underlying stream", e);
        }
    }
    
}
