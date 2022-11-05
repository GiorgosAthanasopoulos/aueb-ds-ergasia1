import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl<T> implements StringStack<T> {
    private final SinglyLinkedList<T> singlyLinkedList;

    public StringStackImpl() {
        singlyLinkedList = new SinglyLinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return singlyLinkedList.isEmpty();
    }

    @Override
    public void push(T item) {
        singlyLinkedList.addLast(item);
    }

    @Override
    public T pop() throws NoSuchElementException {
        return singlyLinkedList.removeLast().val;
    }

    @Override
    public T peek() throws NoSuchElementException {
        return singlyLinkedList.getLast().val;
    }

    @Override
    public void printStack(PrintStream stream) {
        stream.println(singlyLinkedList);
    }

    @Override
    public int size() {
        return singlyLinkedList.size();
    }
}