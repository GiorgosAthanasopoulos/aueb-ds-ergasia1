import java.io.PrintStream;
import java.util.NoSuchElementException;

public class QueueWithOnePointer<T> implements StringQueue<T> {
    private final CircularSinglyLinkedList<T> circularSinglyLinkedList;

    public QueueWithOnePointer() {
        circularSinglyLinkedList = new CircularSinglyLinkedList<>();
    }

    @Override
    public boolean isEmpty() {
        return circularSinglyLinkedList.isEmpty();
    }

    @Override
    public void put(T item) {
        circularSinglyLinkedList.addFirst(item);
    }

    @Override
    public T get() throws NoSuchElementException {
        return circularSinglyLinkedList.removeLast().val;
    }

    @Override
    public T peek() throws NoSuchElementException {
        return circularSinglyLinkedList.getLast().val;
    }

    @Override
    public void printQueue(PrintStream stream) {
        stream.println(circularSinglyLinkedList);
    }

    @Override
    public int size() {
        return circularSinglyLinkedList.size();
    }
}
