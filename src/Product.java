import java.util.*;
import java.io.Serializable;
public abstract class Product implements Serializable {
    private String productID;
    private String productname;
    private int quantity;
    private int price;

    static Scanner input = new Scanner(System.in);

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price ;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public abstract String getInfo();


    public Product(String productID, String productname, int quantity, int price) {
        this.productID = productID;
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "productID    - " + productID + "\n" +
                "productname  - " + productname + "\n" +
                "quantity     - " + quantity + "\n" +
                "price        - " + price + "\n";
    }

}
