import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main2 {
    public static void main(String[] args) {

        Set<Product> productSet = new HashSet<>();

        BufferedReader br = null;
        String line;
        String path = "/Users/maaam/git_mac/goorm-training/java-myCart/src/data.csv";
        List<Product> myProducts = new ArrayList<>();

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));

            while ((line = br.readLine()) != null) {
                String[] temp = line.split(",");
                Product product = new Product(temp[0], temp[1], Integer.parseInt(temp[2]));
                productSet.add(product);
                myProducts.add(product);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //상품 목록 잘 들어갔는지 확인
        System.out.println("상품 목록 확인");
        for (Product product : productSet) {
            System.out.println(product.getName() + " : " + product.getPrice());
        }



        Cart myCart = new Cart();

        myCart.addProduct(myProducts.get(0), 2);
        myCart.addProduct(myProducts.get(1), 3);

        myCart.removeProduct(myProducts.get(0), 2);
        myCart.removeProduct(myProducts.get(1), 1);

        myCart.showItems();

        myCart.removeProduct(myProducts.get(0), 1);
        System.out.println(myCart.showItemsStream());

        myCart.addProduct(myProducts.get(0), 1);
        System.out.println(myCart.showItemsStream());


    }
}
