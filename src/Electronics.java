import java.io.ByteArrayInputStream;

public class Electronics extends Product{
    private String brand;
    private int warranty;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    @Override
    public String getInfo(){
        return (brand + ", " + warranty + " weeks warranty");
    }

    public Electronics(String productID, String productname, int quantity, int price, String brand, int warranty) {
        super(productID, productname, quantity, price);
        this.brand = brand;
        this.warranty = warranty;
    }

    @Override
    public String toString() {
        return  "\nCategory     - Electronics \n" +
                super.toString() +
                "brand        - " + brand + "\n"  +
                "warranty     - " + warranty + " weeks \n" ;
    }
}
