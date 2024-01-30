import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame{

    public void run(ArrayList<Product> products, WestminsterShoppingManager manager) {

        // Creating instances of ShoppingCart, SelectedProductDetails, and ProductTable
        ShoppingCart shoppingCart = new ShoppingCart();
        SelectedProductDetails productDetails = new SelectedProductDetails(products, manager, shoppingCart);
        ProductTable productTable = new ProductTable(products, productDetails, shoppingCart);

        // Setting the layout of the JFrame with a 2x1 grid
        setLayout(new GridLayout(2,1));

        // Adding ProductTable and SelectedProductDetails components to the JFrame
        add(productTable);
        add(productDetails);

        // Configuring the properties of the frame
        setTitle("Westminster shopping manager");
        setSize(650,650);    //set size of the window
        setVisible(true);               // when opened it would come in front and be focused
        setLocationRelativeTo(null);    // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
