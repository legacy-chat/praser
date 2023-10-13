package ca.awoo.praser.character;

import java.io.IOException;
import java.io.InputStream;

import ca.awoo.praser.InputStreamOf;
import ca.awoo.praser.StreamException;

/**
 * An {@link InputStreamOf} that reads characters from an {@link InputStream}.
 */
public class CharacterStream extends InputStreamOf<Character>{

    private InputStream input;

    /**
     * Creates a new {@link CharacterStream}.
     * @param input the underlying stream to read from
     */
    public CharacterStream(InputStream input){
        this.input = input;
    }

    /**
     * Reads the next character from the underlying stream.
     * @return the next character in the stream
     * @throws StreamException if an exception occurs while reading the underlying stream
     */
    @Override
    protected Character readStream() throws StreamException {
        try{
            int next = input.read();
            if(next == -1){
                return null;
            }else{
                return (char)next;
            }
        }catch(IOException e){
            throw new StreamException("An IOException occured while reading the underlying stream", e);
        }
    }
        
}
