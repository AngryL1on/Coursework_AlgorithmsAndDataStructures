public class Node {
    Route data;
    Node next;
    Node prev;

    public Node(Route data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
