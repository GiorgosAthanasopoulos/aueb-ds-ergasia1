class Node<T> {
    T val;
    Node<T> next;

    public Node() {

    }
    public Node(T v) {
        val = v;
    }
    public Node(T v, Node<T> n) {
        val = v;
        next = n;
    }
}
