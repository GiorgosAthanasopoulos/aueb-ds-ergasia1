import java.util.NoSuchElementException;

public class CircularSinglyLinkedList<T> implements LinkedList<T> {
    private int size = 0;

    private Node<T> tail;

    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void addFirst(T val) {
        Node<T> node = new Node<>(val);

        if(size == 0) {
            tail = node;
        } else {
            node.next = tail.next;
            tail.next = node;
        }

        size++;
    }
    @Override
    public void addLast(T val) {
        Node<T> node = new Node<>(val);

        if (size != 0) {
            node.next = tail.next;
            tail.next = node;
        }
        tail = node;

        size++;
    }

    @Override
    public Node<T> removeFirst() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if(size == 1) {
            Node<T> tailCopy = tail;
            tail = null;
            size--;
            return tailCopy;
        } else {
            Node<T> oldHead = tail.next;
            tail.next = tail.next.next;
            size--;
            return oldHead;
        }
    }
    @Override
    public Node<T> removeLast() throws NoSuchElementException {
        if(size == 0) {
            throw new NoSuchElementException();
        } else if(size == 1) {
            Node<T> oldTail = tail;
            tail = null;
            size--;
            return oldTail;
        } else {
            Node<T> oldTail = tail;
            // TODO tail.prev.next = tail.next
            // TODO tail = tail.prev
            size--;
            return oldTail;
        }
    }

    @Override
    public Node<T> getFirst() throws NoSuchElementException {
        if(size == 0) {
            throw new NoSuchElementException();
        }

        return tail.next;
    }
    @Override
    public Node<T> getLast() throws NoSuchElementException {
        if(size == 0) {
            throw new NoSuchElementException();
        }

        return tail;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        buffer.append(getClass().getName());
        buffer.append("@");
        buffer.append(Integer.toHexString(hashCode()));
        buffer.append(" : ");

        Node<T> head = tail.next;

        while(head != tail) {
            buffer.append(head.val);
            buffer.append(" ");

            head = head.next;
        }

        return buffer.toString();
    }
}
