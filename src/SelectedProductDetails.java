import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SelectedProductDetails extends JPanel {

    private final ArrayList<JLabel> productLabels = new ArrayList<>();      //// ArrayList to store labels for displaying product details
    JLabel selectedProductLabel = new JLabel("Selected Product - Details");
    private Product selectedProduct;            // Selected product instance

    public SelectedProductDetails(ArrayList<Product> products, WestminsterShoppingManager manager, ShoppingCart shoppingCart){

        // Set layout and border for the main panel
        setLayout(new GridLayout(2,1));
        setBorder(new EmptyBorder(10,30,-190,30));

        //Product details panel
        JPanel productDetailsPanel = new JPanel(new GridLayout(0,1));
        productDetailsPanel.setBorder(new EmptyBorder(0,10,0,0));

        // Array of labels for different product details
        String[] nameLabels = {
                "Product ID:",
                "Category:",
                "Name:",
                "Size:",
                "Colour:",
                "Items Available:"
        };

        // Adding selectedProductLabel to productDetailsPanel
        productDetailsPanel.add(selectedProductLabel);

        // Creating and adding product labels to the product details panel
        for (String labelText : nameLabels) {
            JLabel label = new JLabel(labelText);
            Font plainFont = new Font(label.getFont().getFamily(), Font.PLAIN, label.getFont().getSize());
            label.setFont(plainFont);
            productLabels.add(label);
            productDetailsPanel.add(label);
        }

        //Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(new EmptyBorder(8,0,0,0));

        // Button to add selected product to the shopping cart
        JButton cartButton = new JButton("Add to Shopping cart");
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check if there is enough quantity available before adding to the cart
                if(selectedProduct.getQuantity() <= 0 ) return;
                selectedProduct.setQuantity(selectedProduct.getQuantity() - 1);
                updateSelectedProduct(selectedProduct);
                shoppingCart.addItem(selectedProduct);
                manager.saveProduct(products, "");
            }
        });
        buttonPanel.add(cartButton);

        // // Add productDetailsPanel and buttonPanel to the main panel in the GUI.
        add(productDetailsPanel);
        add(buttonPanel);
    }

    // Method to update selected product information into the relevant labels in the product detail section
    public void updateSelectedProduct(Product prod){
        selectedProduct = prod;
        productLabels.get(0).setText("Product ID: " + selectedProduct.getProductID());
        productLabels.get(1).setText("Category: " + ((selectedProduct instanceof Electronics) ? "Electronics" : "Clothing"));
        productLabels.get(2).setText("Name: " + selectedProduct.getProductname());
        if(selectedProduct instanceof Electronics){
            productLabels.get(3).setText("Brand: " + ((Electronics) selectedProduct).getBrand());
            productLabels.get(4).setText("Warranty: " + ((Electronics) selectedProduct).getWarranty() + " Weeks");
        }
        else if(selectedProduct instanceof Clothing){
            productLabels.get(3).setText("Size: " + ((Clothing) selectedProduct).getSize());
            productLabels.get(4).setText("Color: " + ((Clothing) selectedProduct).getColour());
        }
        productLabels.get(5).setText("Items Available: " + selectedProduct.getQuantity());
    }
}
