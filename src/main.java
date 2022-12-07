import java.util.Scanner;
import java.sql.*;

public class main {
    //Change the url, user, and databasePassword accordingly for it to work on your machine
    private final static String url = "jdbc:postgresql://localhost/COMP 3005 Project";
    private final static String user = "postgres";
    private final static String databasePassword = "mot38_Rot";

    public static void createUser() {
        Scanner input = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(url, user, databasePassword);
             Statement statement = connection.createStatement();
        ) {
            try {
                System.out.println("Please enter a username:");
                String userName = input.nextLine();
                System.out.println("Please enter your password: ");
                String passWord = input.nextLine();
                System.out.println("What is your name?");
                String name = input.nextLine();
                System.out.println("What is your email? ");
                String email = input.nextLine();
                System.out.println("What is you address?");
                String address = input.nextLine();
                System.out.println("What is your phone number? ");
                String phone = input.nextLine();
                System.out.println("If you are a customer, enter 1. If you are an owner, enter 2");

                String userType = " ";
                if(input.nextInt() == 1){
                    userType = "customer";
                }else if(input.nextInt() == 2) {
                    userType = "owner";
                }

                statement.executeUpdate("insert into users values ('" + name + "', '" + userName + "','" + passWord + "','" + email + "','" + address + "','" + phone + "','" + userType + "');");
                System.out.println("\nCongratulations! You have created your profile! You will now be redirected to the store.\n");
                loginUser(userName, passWord);

            } catch (SQLException sqle) {
                System.out.println("Could not find user " + sqle);
            }

        } catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public static void loginUser(String username, String password){

        ResultSet result;
        String type = null;
        String cust = "customer";

        try (Connection connection = DriverManager.getConnection(url, user, databasePassword);
             Statement statement = connection.createStatement();
        ) {
            try {
                result = statement.executeQuery(
                        "select type from users where username = ('" + username + "') and password = ('" + password + "')");
                result.next();
                type = result.getString("type");

                if(type.equals(cust)){ //if it's a customer we show certain menu

                    System.out.println("Welcome Customer! \n");

                    Consumer c = new Consumer(statement,connection, username);
                    c.customerMenu();

                } else { // owner/manager // if it's a manager we show another menu

                    System.out.println("Welcome Owner! \n");

                    Owner o = new Owner(statement, connection, username);
                    o.ownerMenu();

                }

            } catch (SQLException sqle) {
                System.out.println("Could not find user " + sqle);
            }

        } catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }

    }

    public static void main(String[] args) throws SQLException {
        Scanner login = new Scanner(System.in);
        Scanner input = new Scanner(System.in);

        System.out.println("Hello and welcome to LookInnaABookStore!");
        System.out.println("If you are an existing user, enter 1");
        System.out.println("If you are an new user, enter 2");

        if (input.nextInt() == 1){
            System.out.println("Enter Username: \n");
            String userName = login.nextLine();
            System.out.println("Enter Password: \n");
            String password = login.nextLine();
            loginUser(userName, password);
        }else{
            createUser();
        }
    }
}
