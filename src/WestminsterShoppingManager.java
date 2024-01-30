import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {

    static Scanner input = new Scanner(System.in);

    public void run(ArrayList<Product> products) {

        while (true) {                                              //loops the program if the user wants to choose another option

            System.out.println("""
                    
                    -------------------------------------------------
                    Please select an option:
                    1) Add a Product
                    2) Delete a Product
                    3) Print the list of the products
                    4) Save in a file
                    0) Quit
                    -------------------------------------------------""");

            // Read user input
            System.out.print("Enter option: ");
            int option = intInput("Enter option: ");

            // Perform action based on user input
            switch (option) {
                case 0 -> System.exit(0);
                case 1 -> addProduct(products);
                case 2 -> deleteProduct(products);
                case 3 -> printProduct(products);
                case 4 -> saveProduct(products, "Products saved to file successfully.");

                default -> System.out.println("Invalid option, please try again.");
            }

        }

    }

    // Method used to add new products to the system
    public void addProduct(ArrayList<Product> products) {

        String answer = "y";
        do {
            System.out.println("""

                    -------------------------------------------------
                    Please select an option:
                    0) Return to Main menu
                    1) Add Electronic product
                    2) Add Clothing product
                    -------------------------------------------------""");

            System.out.print("Enter option : ");
            int option = intInput("Enter option: ");

            if (option == 0) {
                return;
            } else if (option == 1 || option == 2) {
                if (products.size() < 50) {

                    // input product details
                    System.out.print("Enter productId: ");
                    String productID = input.next();
                    productExists(products, productID);

                    System.out.print("Enter productname: ");
                    String productname = input.next();
                    System.out.print("Enter quantity: ");
                    int quantity = intInput("Enter quantity: ");
                    System.out.print("Enter price: ");
                    int price = intInput("Enter price: ");

                    if (option == 1) {

                        // details specific for electronics
                        System.out.print("Enter brand: ");
                        String brand = input.next();
                        System.out.print("Enter warranty period: ");
                        int warranty = intInput("Enter warranty period: ");

                        // Creating an electronics object and adding it to the arraylist
                        Electronics electronics = new Electronics(productID, productname, quantity, price, brand, warranty);
                        products.add(electronics);

                    } else if (option == 2) {

                        // details specific for Clothing
                        System.out.print("Enter size [Small (s), Medium (m), Large(l): ");
                        String size = input.next();
                        System.out.print("Enter colour: ");
                        String colour = input.next();

                        // Creating a clothing object and adding it to the arraylist
                        Clothing clothing = new Clothing(productID, productname, quantity, price, size, colour);
                        products.add(clothing);
                    }
                    System.out.println("\nProduct added successfully");


                } else {
                    System.out.println("Maximum products reached");
                }
            } else {
                System.out.println("Invalid input please try again");
                continue;
            }

            System.out.println("Number of products in the system = " + products.size());
            System.out.print("\nWould you like to add another product ? (Y/N): ");
            answer = input.next().toLowerCase();
        } while (answer.equals("y"));
    }

    // Method to delete a product from the system
    public void deleteProduct(ArrayList<Product> products) {

        String answer;
        boolean deleteproduct = false;
        do {
            System.out.print("\nEnter Product Id: ");
            String id = input.next();
            for (Product product : products) {
                if (product.getProductID().equals(id)) {
                    System.out.println(product);
                    System.out.print("Is this the product you would like to delete ? (Y/N): ");
                    String option = input.next().toLowerCase();

                    if (option.equals("y")){
                        products.remove(product);
                        deleteproduct = true;
                        System.out.println("Product successfully deleted");
                        System.out.println("Number of products in the system = " + products.size());
                        break;
                    }
                    System.out.println("Delete cancelled");
                    return;
                }
            }
            if (!deleteproduct) {
                System.out.println("Product Not Found");
            }
            System.out.print("\nWould you like to delete another product ? (Y/N): ");
            answer = input.next().toLowerCase();
        } while (answer.equals("y"));
    }

    // Method to print the list of products in the system
    public void printProduct(ArrayList<Product> products) {
        products.sort(Comparator.comparing(Product::getProductID));
        for (Product product : products) {
            System.out.println(product);
        }
    }

    // Method to save products to a file
    public void saveProduct(ArrayList<Product> products, String message) {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("products.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Writing the ArrayList to the file
            objectOutputStream.writeObject(products);
            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println(message);

        } catch (IOException e) {
            System.out.println("Error saving products to file products.txt");
        }
    }

    // Method to check if a product with the given productID already exists and if required to increase its quantity
    public void productExists(ArrayList<Product> products, String productID) {

        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                System.out.println("Product already exists");

                System.out.println("""

                        -------------------------------------------------
                        How would you like to proceed\s

                        0) Return to Main menu
                        1) Add a different productID\s
                        2) Update Product quantity\s
                        -------------------------------------------------""");

                System.out.print("Enter option: ");
                int option = intInput("Enter option: ");

                if (option == 1) {
                    addProduct(products);

                } else if (option == 2) {
                    System.out.print("Enter quantity to be added: ");
                    int addQuantity = intInput("Enter quantity to be added: ");
                    product.setQuantity(product.getQuantity() + addQuantity);
                    System.out.println("Quantity Updated Successfully");
                    System.out.println("New Quantity = " + product.getQuantity());

                    // Return to main menu after successfully updating the quantity
                    run(products);
                } else {
                    run(products);
                }
            }
        }
    }

    public int intInput(String print) {
        while (true) {
            try {
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer\n");
                input.nextLine(); // this would take in the wrong input else it would just be an infinite loop
                System.out.print(print);
            }
        }
    }
}
