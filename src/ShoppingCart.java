import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {

    // HashMap to store purchased items and their quantities
    private final HashMap<Product, Integer> purchasedItems = new HashMap<>();
    private int electronicTotal = 0;        // counter for electronic items
    private int clothingTotal = 0;          // counter for clothing items

    // Method to display the shopping cart
    public void cart(){

        // Create the main frame
        JFrame frame = new JFrame("Shopping Cart");
        frame.setLayout(new GridLayout(2,0));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel to display the items in a table
        JPanel cartTable = new JPanel();
        cartTable.setBorder(new EmptyBorder(15,0,0,0));

        // Panel to display the total and discounts
        JPanel cartTotal = new JPanel();

        // Creating a table to display items
        JTable itemsTable = new JTable();
        String[] columnNames = {"Product","Quantity","Price"};
        JTableHeader header = itemsTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        itemsTable.setRowHeight(50);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        itemsTable.setDefaultRenderer(Object.class, centerRenderer);

        // ArrayList to store rows of the table
        ArrayList<String[]> tableRows = new ArrayList<>();

        // Add all details of the purchased items into the table row
        for (Product product : purchasedItems.keySet()) {
            String[] row = {formatMultilineContent(
                    product.getProductID(),
                    product.getProductname(),
                    product.getInfo()),
                    Integer.toString(purchasedItems.get(product)),
                    Float.toString(product.getPrice() * purchasedItems.get(product)) + " £"};
            tableRows.add(row);
        }

        // Convert ArrayList to a 2D array
        String[][] tableDetails = new String[tableRows.size()][];
        tableRows.toArray(tableDetails);

        // Create the table model
        TableModel model = new DefaultTableModel(tableDetails, columnNames);
        itemsTable.setModel(model);
        itemsTable.getTableHeader().setReorderingAllowed(false);
        itemsTable.setDefaultEditor(Object.class, null);

        // Add a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setPreferredSize(new Dimension(450, 160));
        cartTable.add(scrollPane);

        // Add Labels with all the correct details about the total and discounts
        float total = calculateTotalAmount();
        JLabel totalLabel = new JLabel("Total -   " + total + " £");

        //Set alignment and font for label
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.PLAIN));

        float firstPurchaseDiscountCheck = (float)(total*0.1);
        JLabel firstPurchaseDiscount = new JLabel("First Purchase Discount (10%) -     " + firstPurchaseDiscountCheck + " £");
        firstPurchaseDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        firstPurchaseDiscount.setFont(firstPurchaseDiscount.getFont().deriveFont(Font.PLAIN));

        float sameCategoryDiscountCheck = (electronicTotal >= 3 || clothingTotal >= 3) ? (float) (total * 0.2) : 0;
        JLabel sameCategoryDiscount = new JLabel("Three Items in same Category Discount (20%) -     " + sameCategoryDiscountCheck + " £");
        sameCategoryDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
        sameCategoryDiscount.setFont(sameCategoryDiscount.getFont().deriveFont(Font.PLAIN));

        float finalTotalCalculation = total - sameCategoryDiscountCheck - firstPurchaseDiscountCheck;
        JLabel finalTotal = new JLabel("Final Total -     " + finalTotalCalculation + " £");
        finalTotal.setHorizontalAlignment(SwingConstants.RIGHT);

        // Setting the layout for the total panel
        cartTotal.setLayout(new GridLayout(5,0));
        cartTotal.add(totalLabel);
        cartTotal.add(firstPurchaseDiscount);
        cartTotal.add(sameCategoryDiscount);
        cartTotal.add(finalTotal);
        cartTotal.setBorder(new EmptyBorder(30,0,0,50));

        // Adding the components to the main frame
        frame.add(cartTable);
        frame.add(cartTotal);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Method to add an item to the shopping cart
    public void addItem(Product prod){
        if(purchasedItems.containsKey(prod)){
            purchasedItems.put(prod, purchasedItems.get(prod) + 1);
        }
        else{
            purchasedItems.put(prod, 1);
            if(prod instanceof Electronics) electronicTotal += 1;
            else clothingTotal += 1;
        }
    }

    // Method to calculate the total amount
    public float calculateTotalAmount(){
        float total = 0;

        for (Product product: purchasedItems.keySet()) {
            total += product.getPrice() * purchasedItems.get(product);
        }

        return total;
    }

    // Method to add line breaks for the table first column when displaying multiple items
    public String formatMultilineContent(String... lines) {
        return "<html><center>" + String.join("<br>",lines);
    }

}
