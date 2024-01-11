public class Node<T> {

    private T data;
    private Node<T> next; // 다음 노드를 가리키는 값을 담기 위함


    // Node 를 생성할 때 data로만 Node 객체를 생성해주고 다른 노드를 가리킬지는 생성할 때 필요없다.
    public Node(T data) {
        this.data = data;
        this.next = null;
    }

    // Getter
    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    // Setter
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
