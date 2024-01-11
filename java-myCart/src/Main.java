import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Set<Product> productSet = new HashSet<>();

        Product product1 = new Product("1", "칫솔", 1000);
        Product product2 = new Product("2", "치약", 2000);

        productSet.add(product1);
        productSet.add(product2);

        System.out.println("상품 목록 확인");
        for (Product product : productSet) {
            System.out.println(product.getName() + " : " + product.getPrice());
        }

        Cart myCart = new Cart();

        myCart.addProduct(product1, 2);
        myCart.addProduct(product2, 3);

        myCart.removeProduct(product1, 2);
        myCart.removeProduct(product2, 1);

        myCart.showItems();

        myCart.removeProduct(product1, 1);
        System.out.println(myCart.showItemsStream());

        myCart.addProduct(product1, 1);
        System.out.println(myCart.showItemsStream());

    }
}