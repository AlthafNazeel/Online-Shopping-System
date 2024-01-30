import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;
import java.io.*;

// JUnit test class for WestminsterShoppingManager
public class WestminsterShoppingManagerTest {

    // Variables for capturing and comparing console output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    // Instances of WestminsterShoppingManager and ArrayList to be tested
    private WestminsterShoppingManager shoppingManager;
    private ArrayList<Product> products;

    // Method to set up the testing environment before each test case
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        shoppingManager = new WestminsterShoppingManager();
        products = new ArrayList<>();
    }

    // Method to clean up after each test case
    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
        products.clear();
    }

    // Test case for adding products
    @Test
    public void testAddProduct() {

        assertEquals(0, products.size());

        Electronics electronics = new Electronics("E001", "Laptop", 50, 750, "Hp", 12);
        products.add(electronics);

        // Assertions for the added product
        assertEquals(1, products.size());
        Product addedProduct = products.get(0);

        assertTrue(addedProduct instanceof Electronics);
        assertEquals("E001", addedProduct.getProductID());
        assertEquals("Laptop", addedProduct.getProductname());
        assertEquals(50, addedProduct.getQuantity());
        assertEquals(750, addedProduct.getPrice());
        assertEquals("Hp", ((Electronics) addedProduct).getBrand());
        assertEquals(12, ((Electronics) addedProduct).getWarranty());

        Clothing clothing = new Clothing("C001", "Shirt", 50, 800, "S", "Pink");
        products.add(clothing);

        assertEquals(2, products.size());
        Product clothingProduct = products.get(1);

        assertTrue(clothingProduct instanceof Clothing);
        assertEquals("C001", clothingProduct.getProductID());
        assertEquals("Shirt", clothingProduct.getProductname());
        assertEquals(50, clothingProduct.getQuantity());
        assertEquals(800, clothingProduct.getPrice());
        assertEquals("S", ((Clothing) clothingProduct).getSize());
        assertEquals("Pink", ((Clothing) clothingProduct).getColour());
    }

    // Test case for deleting products
    @Test
    public void testDeleteProduct() {
        Product electronicProduct = new Electronics("E001", "Laptop", 50, 750, "Hp", 12);
        products.add(electronicProduct);

        // Assertions before and after removal
        assertEquals(1, products.size());
        products.remove(electronicProduct);
        assertEquals(0, products.size());

        Product clothingProduct = new Clothing("C001", "Shirt", 50, 800, "S", "Pink");
        products.add(clothingProduct);

        assertEquals(1, products.size());
        products.remove(clothingProduct);
        assertEquals(0, products.size());

    }

    // Test case for printing product details
    @Test
    public void testPrintProduct() {
        // Preparing the product
        Product electronicProduct = new Electronics("E001", "Laptop", 50, 750, "Hp", 12);
        Product clothingProduct = new Clothing("C001", "Shirt", 50, 800, "S", "Pink");
        products.add(electronicProduct);
        products.add(clothingProduct);

        // Call the print_product method and capture the console output
        shoppingManager.printProduct(products);

        // Check if the output contains product information
        String output = outContent.toString();
        assertTrue("Output should contain product information.", output.contains("E001") && output.contains("Laptop"));
        assertTrue("Output should contain product information.", output.contains("C001") && output.contains("Shirt"));
    }

    // Test case for saving products to a file
    @Test
    public void testSaveProduct() {
        // Prepare products
        Product product = new Electronics("E001", "Laptop", 50, 750, "Hp", 12);
        products.add(product);

        // Call the save_product method and capture the console output
        shoppingManager.saveProduct(products, "Products saved to file successfully.");

        // Check if the output confirms saving to the file
        String output = outContent.toString();
        assertTrue("Output should confirm saving to file.", output.contains("Products saved to file successfully."));

    }

}



