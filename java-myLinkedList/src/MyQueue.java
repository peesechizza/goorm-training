public class MyQueue<T> {

    private MyLinkedList<T> myLinkedList = new MyLinkedList<>();

    // enqueue
    public void enqueue(T item) {
        myLinkedList.add(item);
    }

    // dequeue
    public T dequeue() {
        if (myLinkedList.isEmpty()) {
            throw new IllegalStateException("empty queue");
        }

        T frontItem = myLinkedList.get(0);
        myLinkedList.delete(0);
        return frontItem;
    }

    // peek
    public T peek() {
        if (myLinkedList.isEmpty()) {
            throw new IllegalStateException("empty queue");
        }

        T frontItem = myLinkedList.get(0);
        return frontItem;
    }
}
