import java.io.PrintStream;
import java.util.NoSuchElementException;

/**
 * Defines the methods for a StringStack that handles String items
 */
public interface StringStack<T> {

    /**
     * @return true if the stack is empty
     */
    boolean isEmpty();

    /**
     * insert a String item to the stack
     */
    void push(T item);

    /**
     * remove and return the item on the top of the stack
     *
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @SuppressWarnings("UnusedReturnValue")
    T pop() throws NoSuchElementException;

    /**
     * return without removing the item on the top of the stack
     *
     * @return the item on the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    T peek() throws NoSuchElementException;

    /**
     * print the elements of the stack, starting from the item
     * on the top,
     * to the stream given as argument. For example,
     * to print to the standard output you need to pass System.out as
     * an argument. E.g.,
     * printStack(System.out);
     */
    void printStack(PrintStream stream);

    /**
     * return the size of the stack, 0 if it is empty
     *
     * @return the number of items currently in the stack
     */
    int size();

}