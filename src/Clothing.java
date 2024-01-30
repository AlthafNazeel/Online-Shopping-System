public class Clothing extends Product{
    private String size;
    private String colour;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String getInfo(){
        return size.toUpperCase() + ", " + colour;
    }

    @Override
    public String toString() {
        return  "\nCategory     - Clothing \n" +
                super.toString() +
                "size         - " + size + "\n" +
                "colour       - " + colour ;
    }

    public Clothing(String productID, String productname, int quantity, int price, String size, String colour) {
        super(productID, productname, quantity, price);
        this.size = size;
        this.colour = colour;
    }
}
