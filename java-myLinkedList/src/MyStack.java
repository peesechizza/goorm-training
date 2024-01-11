public class MyStack<T> {
    public MyLinkedList<T> myLinkedList = new MyLinkedList<>();

    // push
    public void push(T item) {
        myLinkedList.add(item);
    }

    // pop
    public T pop() {
        if (myLinkedList.isEmpty()) { // 아무것도 없을 때 꺼내면 예외 처리
            throw new IllegalStateException("empty stack");
        }

        // pop 할 때 사이즈 조정이 필요하다. 가장 마지막 값을 없애준다.
        int lastIndex = myLinkedList.size() - 1;
        T top = myLinkedList.get(lastIndex);
        myLinkedList.delete(lastIndex);
        return top;
    }

    // peek (맨 위에 있는 요소를 확인)
    public T peek() {
        if (myLinkedList.isEmpty()) {
            throw new IllegalStateException("empty stack");
        }

        int lastIndex = myLinkedList.size() - 1;
        T top = myLinkedList.get(lastIndex);
        return top;
    }
}
