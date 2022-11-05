public interface LinkedList<T> {
    int size();
    boolean isEmpty();
    void addFirst(T val);
    void addLast(T val);
    Node<T> removeFirst();
    Node<T> removeLast();
    Node<T> getFirst();
    Node<T> getLast();
    @Override
    public String toString();
}
