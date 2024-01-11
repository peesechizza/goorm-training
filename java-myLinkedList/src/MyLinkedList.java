import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable{

    private Node<T> head; // LinkedList는 첫 번째 노드의 값을 알아야 데이터를 다룰 수 있기 때문에 첫 번째 노드를 가리키는 head 값 필요

    private int size = 0; // LinkedList의 size 값을 나타내기 위함

    // add() : MyLinkedList 의 마지막 노드에 data 를 추가 할 수 있습니다.
    public void add(T data) {
        Node<T> node = new Node<>(data);

        if (size == 0) { // LinkedList 가 비어 있는 경우
            head = node;
        } else { // LinkedList 가 1개 이상인 경우
            Node<T> current = head; // 현재 노드 임시 변수

            while (current.getNext() != null) { // 현재 노드에 다음 노드가 있을 경우 탐색
                current = current.getNext();
            }

            current.setNext(node);
        }

        this.size++;
    }

    // get(index i): MyLinkedList 의 i 번째 노드의 data 를 return 합니다.
    public T get(int index) {
        // size 보다 크거나, 0보다 작은 인덱스를 가리키는 예외 처리
        if (index < 0 || this.size <= index) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = this.head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current.getData();
    }

    // delete(index i): MyLinkedList 의 i 번째 노드의 데이터를 삭제합니다.
    public void delete(int index) {
        // 양 끝 값 경계에 대한 예외처리
        if (index < 0 || this.size <= index) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) { // 1번 노드를 삭제하는 경우 2번 노드
            head = head.getNext();
        } else { // index 번째의 노드 탐색
            Node<T> current = this.head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            current.setNext(current.getNext().getNext()); // 2 3 4 번째에서 3번째를 삭제하면 2 -> 4의 순서가 되도록 set
        }
        this.size--;
    }


    // Iterator interface 를 implements 한 후에 구현하여 for-each 로 순회 가능하도록 해봅시다.
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Node<T> current = head; // 맨 처음 head 부터 순회

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException(); // 노드가 없으면 예외 처리
                }
                T data = current.getData();
                current = current.getNext();

                return data;
            }
        };
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

}
