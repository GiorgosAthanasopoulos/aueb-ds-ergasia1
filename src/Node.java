class Node<T> {
    T val;
    Node<T> next;

    // val and next are null by default
    public Node(T v) {
        val = v;
    }

    public Node(T v, Node<T> n) {
        val = v;
        next = n;
    }
}
