import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //Creating Instances of the WestminsterShoppingManager, Users, and GUI class
    private static WestminsterShoppingManager manager = new WestminsterShoppingManager();
    private static Users user = new Users();
    private static GUI gui = new GUI();
    static Scanner input = new Scanner(System.in);


    public static void main(String[] args) {

        // Loads all the product details from th saved file into the Arraylist
        ArrayList<Product> products = loadProducts();

        while (true) {
            System.out.println("""

                    -------------------------------------------------
                    Are you a :
                    1) Manager
                    2) Customer
                    -------------------------------------------------""");

            System.out.print("Please enter (1/2): ");
            int option = manager.intInput("Please enter (1/2): ");

            switch (option) {
                case 0 -> System.exit(0);
                case 1 -> manager.run(products);
                case 2 -> user.run(products, manager);
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Method to load products from the file
    public static ArrayList<Product> loadProducts(){
        ArrayList<Product> loadedProducts = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream("products.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Reading the ArrayList from the file
            loadedProducts = (ArrayList<Product>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
            System.out.println("Products loaded from file successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from the file");
        }

        return loadedProducts;
    }

}