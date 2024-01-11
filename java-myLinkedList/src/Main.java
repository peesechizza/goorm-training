import java.lang.module.FindException;

public class Main {
    public static void main(String[] args) {

        MyLinkedList<String> myLinkedList = new MyLinkedList<>();

        myLinkedList.add("A");
        myLinkedList.add("B");
        myLinkedList.add("C");
        myLinkedList.add("D");

        System.out.println("GET");
        System.out.println(myLinkedList.get(0));
        System.out.println(myLinkedList.get(1));
        System.out.println(myLinkedList.get(2));
        System.out.println(myLinkedList.get(3));

        System.out.println("DELETE");
        myLinkedList.delete(2);

//        for (int i = 0; i < 3; i++) {
//            System.out.println(myLinkedList.get(i));
//        }

        // MyLinkedList 에서 Iterator 클래스를 구현해야함
        for (Object item : myLinkedList) {
            System.out.println(item);
        }
    }
}

