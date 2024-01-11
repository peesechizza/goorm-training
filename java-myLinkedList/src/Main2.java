public class Main2 {
    public static void main(String[] args) {

        MyStack<String> stack = new MyStack<>();

        stack.push("A");
        stack.push("B");
        stack.push("C");

        String popItem = stack.pop();
        System.out.println("popItem = " + popItem);

        String peekItem = stack.peek();
        System.out.println("peekItem = " + peekItem);
    }
}
