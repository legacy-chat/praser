package ca.awoo.praser;

import java.util.Stack;

public class ParseContext<T> {
    private final InputStreamOf<T> input;
    private final Stack<Integer> offset = new Stack<Integer>();

    public ParseContext(InputStreamOf<T> input, int offset) {
        this.input = input;
        this.offset.push(offset);
    }

    public ParseContext(InputStreamOf<T> input) {
        this.input = input;
    }

    public T peek() throws StreamException {
        if(offset.empty()){
            return input.peek();
        }else{
            return input.peek(offset.peek());
        }
    }

    public T peek(int offset) throws StreamException {
        if(this.offset.empty()){
            return input.peek(offset);
        }else{
            return input.peek(this.offset.peek() + offset);
        }
    }

    public T read() throws StreamException {
        if(offset.empty()){
            return input.read();
        }else{
            T value = input.peek(offset.peek());
            offset.push(offset.pop() + 1);
            return value;
        }
    }

    public void push() {
        if(offset.empty()){
            offset.push(0);
        }else{
            offset.push(offset.peek());
        }
    }

    public void pop() {
        offset.pop();
    }

    public void merge() throws StreamException{
        if(offset.size() > 1){
            int offset = this.offset.pop();
            this.offset.pop();
            this.offset.push(offset);
        }else{
            //Consume the last value
            input.consume(offset.pop());
        }
    }
}
