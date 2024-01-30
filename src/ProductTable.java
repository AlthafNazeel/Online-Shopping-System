import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class ProductTable extends JPanel {
    public ProductTable(ArrayList<Product> products, SelectedProductDetails productDetails, ShoppingCart shoppingCart) {

        setLayout(new BorderLayout());

        // Top Panel with label, dropdown, and cart button
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Select Product Category: ");
        label.setBorder(new EmptyBorder(10, 20, 0, 0));

        String[] categories = {"All", "Electronics", "Clothing"};
        JComboBox<String> dropDown = new JComboBox<>(categories);
        dropDown.setPreferredSize(new Dimension(135, 35));
        dropDown.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton cartButton = new JButton("Shopping Cart");
        cartButton.setPreferredSize(new Dimension(120, 25));

        // ActionListener for the Shopping cart Button to open the shopping cart when clicked
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.cart();
            }
        });

        JPanel dropDownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropDownPanel.add(dropDown);

        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cartButtonPanel.add(cartButton);

        topPanel.add(label, BorderLayout.WEST);
        topPanel.add(dropDownPanel, BorderLayout.CENTER);
        topPanel.add(cartButtonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        // Above this all Configuration for the Top Panel the label, dropdown and the button

        // Bottom Panel with the allProductsTable
        JPanel bottomPanel = new JPanel();
        String[] columnNames = {"Product ID (Sort)", "Name", "Category", "Price(£)", "Info"};

        // Creating and configuring all table properties
        JTable allProductsTable = new JTable();

        // Custom cell renderer implemented to highlight rows 'red' if that products quantity is below 4
        allProductsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) rendererComponent).setHorizontalAlignment(SwingConstants.CENTER);
                String id = (String) table.getValueAt(row, 0);
                int selectedRow = table.getSelectedRow();
                for (Product product : products) {
                    if (product.getProductID().equals(id)) {
                        if (product.getQuantity() <= 3) {
                            rendererComponent.setBackground(Color.RED);
                        } else if (row != selectedRow) {
                            rendererComponent.setBackground(table.getBackground());
                        }
                    }
                }
                return rendererComponent;
            }
        });

        allProductsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(allProductsTable);
        scrollPane.setPreferredSize(new Dimension(600, 245));

        bottomPanel.add(scrollPane);
        bottomPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        add(bottomPanel, BorderLayout.CENTER);

        // ActionListener for the dropdown to filter the products based on each seperate category
        dropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) dropDown.getSelectedItem();
                ArrayList<String[]> rows = new ArrayList<>();

                // Filtering products based on which category it falls under
                for (Product product : products) {
                    String type = (product instanceof Electronics) ? "Electronics" : "Clothing";
                    if (type.equals(selectedOption) || Objects.equals(selectedOption, "All")) {
                        String[] row = {product.getProductID(), product.getProductname(), type,
                                Float.toString(product.getPrice()), product.getInfo()};
                        rows.add(row);
                    }
                }

                String[][] tblData = new String[rows.size()][];
                rows.toArray(tblData);

                TableModel model = new DefaultTableModel(tblData, columnNames);
                allProductsTable.setModel(model);
                allProductsTable.getTableHeader().setReorderingAllowed(false);
                allProductsTable.setDefaultEditor(Object.class, null);
                allProductsTable.getColumnModel().getColumn(3).setPreferredWidth(150);
                allProductsTable.setRowHeight(30);
                dropDown.setSelectedItem(selectedOption);
            }
        });

        // Bold the column names in the header
        JTableHeader header = allProductsTable.getTableHeader();
        header.setFont(header.getFont().deriveFont(Font.BOLD));

        // MouseListener for sorting products by ID when the mouse clicks on the header
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = allProductsTable.columnAtPoint(e.getPoint());
                if (!(column == 0)) {
                    sortProductsByID(products, allProductsTable);
                }
            }
        });

        // Listener to update selected product details when a row is selected
        allProductsTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = allProductsTable.getSelectedRow();
                if (selectedRow != -1) {
                    String ID = allProductsTable.getValueAt(selectedRow, 0).toString();

                    for (Product p : products) {
                        if (p.getProductID().equals(ID)) {
                            productDetails.updateSelectedProduct(p);
                        }
                    }
                }
            }
        });


    }

    // Method to sort products by ID when the table header is clicked
    private void sortProductsByID(ArrayList<Product> products, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Sort products by ID using Comparator
        products.sort(Comparator.comparing(Product::getProductID));

        // Refresh the table model
        model.fireTableDataChanged();
    }

}


