import java.util.ArrayList;

public interface ShoppingManager {

    void addProduct(ArrayList<Product> products);
    void deleteProduct(ArrayList<Product> products);
    void printProduct(ArrayList<Product> products);
    void saveProduct(ArrayList<Product> products, String message);


}
