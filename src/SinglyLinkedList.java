import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements LinkedList<T> {
    private int size = 0;

    private Node<T> head;
    private Node<T> tail;

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T val) {
        Node<T> node = new Node<>(val);

        if(size == 0) {
            head = tail = node;
        } else {
            tail.next = node;
            node.next = head;
            head = node;
        }

        size++;
    }
    public void addLast(T val) {
        Node<T> node = new Node<>(val);

        if (size == 0) {
            head = tail = node;
        } else {
            node.next = tail.next;
            tail.next = node;
            tail = node;
        }

        size++;
    }

    public Node<T> removeFirst() throws NoSuchElementException {
        if(size == 0) {
            throw new NoSuchElementException();
        } else if(size == 1) {
            Node<T> oldHead = head;
            head = tail = null;
            size--;
            return oldHead;
        } else {
            Node<T> oldHead = head;
            head = head.next;
            tail.next = head;
            size--;
            return oldHead;
        }
    }
    public Node<T> removeLast() throws NoSuchElementException {
        if(size == 0) {
            throw new NoSuchElementException();
        } else if(size == 1) {
            Node<T> oldHead = head;
            head = tail = null;
            size--;
            return oldHead;
        } else {
            Node<T> oldTail = tail;
            // TODO update tail.prev, tail
            size--;
            return oldTail;
        }
    }

    public Node<T> getFirst() throws NoSuchElementException {
        if(size == 0) {
            throw new NoSuchElementException();
        }

        return head;
    }
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

        Node<T> headCopy = head;

        while(headCopy != null) {
            buffer.append(head.val);
            buffer.append(" ");

            headCopy = headCopy.next;
        }

        return buffer.toString();
    }
}