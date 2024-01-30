import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Users class implements Serializable to allow object serialization
public class Users implements Serializable {

    // Serial version UID for versioning during object serialization
    @Serial
    private static final long serialVersionUID = 4761182068472062139L ;//4761182068472062139L;
    private String username;
    private String password;
    GUI gui = new GUI();
    ArrayList<Users> allUsers = loadUsers();

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void run(ArrayList<Product> products, WestminsterShoppingManager manager) {

        while (true) {
            System.out.println("""

                    Do you wish to Login or Signup
                    -------------------------------------------------
                    1) Login
                    2) Sign Up
                    0) Quit
                    -------------------------------------------------""");

            System.out.print("Please enter option : ");
            int option = manager.intInput("Please enter option : ");

            switch (option) {
                case 0 -> System.exit(0);
                case 1 -> login(products, manager);
                case 2 -> signUp(products, manager);
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Method for when a new user is Signing up
    public void signUp(ArrayList<Product> products, WestminsterShoppingManager manager) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("\nPlease enter Username: ");
            String username = input.next();

            boolean userFound = false;
            for (Users user : allUsers) {
                if (user.getUsername().equals(username)) {
                    userFound = true;
                    System.out.println("Username already exists");
                    System.out.println("""

                            -------------------------------------------------
                            1) Enter different username
                            2) Login
                            -------------------------------------------------""");

                    System.out.print("Please enter - (1/2): ");
                    int option = manager.intInput("Please enter - (1/2): ");

                    switch (option) {
                        case 1 -> {
                            continue;
                        }
                        case 2 -> login(products, manager);
                        default -> System.out.println("Invalid option, please try again.");
                    }
                }
            }

            if (!userFound) {

                String password;
                while (true) {
                    System.out.println("\n(Password should contain a minimum of 6 characters)");
                    System.out.print("Please enter Password: ");
                    password = input.next();

                    if (password.length() < 6) {
                        continue;
                    }

                    while (true) {
                        System.out.print("\nPlease re-enter the password: ");
                        String reenterPassword = input.next();

                        if (!reenterPassword.equals(password)) {
                            System.out.println("Password does not match, Please try again");
                            continue;
                        } else {
                            System.out.println("\nSuccessfully signed up");
                            Users users = new Users(username, password);
                            allUsers.add(users);
                            saveUsers();
                            gui.run(products, manager);
                            return;
                        }
                    }
                }
            }
        }
    }

    // Method to handle the user login process
    public void login(ArrayList<Product> products, WestminsterShoppingManager manager) {
        Scanner input = new Scanner(System.in);

        boolean usernameExists = false;

        while (!usernameExists) {
            System.out.print("\nPlease enter Username: ");
            String username = input.next();

            for (Users user : allUsers) {
                if (user.getUsername().equals(username)) {
                    usernameExists = true;

                    boolean correctPassword = false;
                    while (!correctPassword) {
                        System.out.print("Please enter Password: ");
                        String password = input.next();
                        if (user.getPassword().equals(password)) {
                            gui.run(products, manager);
                            break;
                        } else {
                            System.out.println("Incorrect Password\n");

                            System.out.print("Would you like to try again (Y/N): ");
                            String option = input.next().toLowerCase();

                            if (option.equals("y")) {
                                continue;
                            } else return;
                        }
                    }
                }
            }
            if (!usernameExists) {
                System.out.println("\nUsername does not exist");
                System.out.println("""

                        -------------------------------------------------
                        1) Enter different username
                        2) Signup
                        -------------------------------------------------""");

                System.out.print("Please enter - (1/2): ");
                int option = manager.intInput("Please enter - (1/2): ");

                if (option == 1) {
                    continue;
                } else if (option == 2) {
                    signUp(products, manager);
                }

            }
        }
    }

    // Save all looged in users to a file
    public void saveUsers() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Users.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Writing the ArrayList to the file
            objectOutputStream.writeObject(allUsers);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {

        }
    }


    // Method to load user data from a file
    public static ArrayList<Users> loadUsers() {
        ArrayList<Users> loggedUsers = new ArrayList<>();

        try {
            FileInputStream fileInputStream = new FileInputStream("Users.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Reading the ArrayList from the file
            loggedUsers = (ArrayList<Users>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException ignored) {

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading users");
        }

        return loggedUsers;
    }

}
