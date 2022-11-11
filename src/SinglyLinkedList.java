import java.util.NoSuchElementException;

public class SinglyLinkedList<T> {
    private int size = 0;

    private Node<T> head;
    private Node<T> tail;

    public void push(T val) {
        Node<T> node = new Node<>(val);
        node.next = head;
        head = node;
        if (size == 0) tail = node;
        size++;
    }

    public Node<T> pop() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();

        Node<T> oldHead = head;
        head = head.next;
        if (--size == 0) tail = null;
        return oldHead;
    }

    public void add(T val) {
        Node<T> node = new Node<>(val);

        if (size == 0) head = node;
        else tail.next = node;
        tail = node;
        size++;
    }

    public Node<T> remove() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();

        Node<T> oldHead = head;
        head = head.next;
        if (--size == 0) tail = null;
        return oldHead;
    }

    public Node<T> stackPeek() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();

        return head;
    }

    public Node<T> queuePeek() throws NoSuchElementException {
        if (size == 0) throw new NoSuchElementException();

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
        StringBuilder buffer = new StringBuilder(getClass().getName() + "@" + Integer.toHexString(hashCode()) + " : ");

        Node<T> headCopy = head;

        while (headCopy != null) {
            buffer.append(headCopy.val).append(", ");

            headCopy = headCopy.next;
        }

        return buffer.toString();
    }
}