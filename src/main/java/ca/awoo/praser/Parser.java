package ca.awoo.praser;

public abstract class Parser<TToken, TMatch> {
    /**
     * A match result.
     * @param <TMatch> the type of the parsed object
     */
    public static class Match<TMatch> {
        /**
         * The parsed object. Null if the match was unsuccessful.
         */
        public final TMatch value;
        /**
         * The number of objects to be consumed from the stream. 0 if the match was unsuccessful.
         */
        public final int length;

        /**
         * Creates a new match result.
         * @param value the parsed object
         * @param length the number of objects to be consumed from the stream
         */
        public Match(TMatch value, int length) {
            this.value = value;
            this.length = length;
        }

        /**
         * Returns true if the match was successful.
         * @return true if the match was successful
         */
        public boolean isMatch() {
            return value != null;
        }

        /**
         * Returns true if the match was unsuccessful.
         * @return true if the match was unsuccessful
         */
        public boolean isNoMatch() {
            return value == null;
        }

        @Override
        public String toString() {
            return String.format("Match(%s, %d)", value, length);
        }
    }

    /**
     * Parses the next object in the stream without consuming any objects from the stream.
     * <p>
     * In order for combinators to work, the need to find a potential match without consuming any objects from the stream.
     * When implementing this method, you should use the peek methods of the stream to find a potential match.
     * </p>
     * @param input the stream to parse from
     * @return The match result. If the match was successful, the value should be the parsed object and the length should be the number of objects to be consumed from the stream. If the match was unsuccessful, the value should be null and the length should be 0.
     * @throws ParseException if an exception occurs while parsing the input
     */
    public Match<TMatch> parse(InputStreamOf<TToken> input) throws ParseException{
        return parse(input, 0);
    }

    /**
     * Parses the next object in the stream at the specified offset without consuming any objects from the stream.
     * @param input the stream to parse from
     * @param offset the offset from the current position
     * @return The match result. If the match was successful, the value should be the parsed object and the length should be the number of objects to be consumed from the stream. If the match was unsuccessful, the value should be null and the length should be 0.
     * @throws ParseException if an exception occurs while parsing the input
     */
    public abstract Match<TMatch> parse(InputStreamOf<TToken> input, int offset) throws ParseException;
}
