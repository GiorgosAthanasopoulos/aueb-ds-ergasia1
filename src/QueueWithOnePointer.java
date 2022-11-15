import java.io.PrintStream;
import java.util.NoSuchElementException;

public class QueueWithOnePointer<T> implements StringQueue<T> {

    static class CircularSinglyLinkedList<T> {
        private int size = 0;

        private Node<T> tail;

        public void add(T val) {
            Node<T> node = new Node<>(val);

            if (size == 0)
                tail.next = node;
            tail = node;
            size++;
        }

        public Node<T> remove() throws NoSuchElementException {
            if (size == 0)
                throw new NoSuchElementException();

            Node<T> oldHead = tail.next;
            tail.next = tail.next.next;
            if (--size == 0)
                tail = null;
            return oldHead;
        }

        public Node<T> peek() throws NoSuchElementException {
            if (size == 0)
                throw new NoSuchElementException();

            return tail;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            Node<T> head = tail.next;

            while (head != tail) {
                buffer.append(head.val).append(", ");
                head = head.next;
            }

            return buffer.toString();
        }
    }

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
        circularSinglyLinkedList.add(item);
    }

    @Override
    public T get() throws NoSuchElementException {
        return circularSinglyLinkedList.remove().val;
    }

    @Override
    public T peek() throws NoSuchElementException {
        return circularSinglyLinkedList.peek().val;
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
