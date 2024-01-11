public class Main3 {
    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>();

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        String item = queue.dequeue();
        System.out.println("item = " + item);

        String peekItem = queue.peek();
        System.out.println("peekItem = " + peekItem);

        // 예외 확인
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }
}
