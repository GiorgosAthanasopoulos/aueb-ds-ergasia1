import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringQueueImpl<T> implements StringQueue<T> {
    private final SinglyLinkedList<T> singlyLinkedList;

    public StringQueueImpl() {
        singlyLinkedList = new SinglyLinkedList<>();
    }
    @Override
    public boolean isEmpty() {
        return singlyLinkedList.isEmpty();
    }

    @Override
    public void put(T item) {
        singlyLinkedList.addFirst(item);
    }

    @Override
    public T get() throws NoSuchElementException {
        return singlyLinkedList.removeLast().val;
    }

    @Override
    public T peek() throws NoSuchElementException {
        return singlyLinkedList.getLast().val;
    }

    @Override
    public void printQueue(PrintStream stream) {
        stream.println(singlyLinkedList);
    }

    @Override
    public int size() {
        return singlyLinkedList.size();
    }
}
