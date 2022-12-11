import java.util.Scanner;
import java.sql.*;

public class main {
    //Change the url, user, and databasePassword accordingly for it to work on your machine
    private final static String url = "jdbc:postgresql://localhost/COMP 3005 Project";
    private final static String user = "postgres";
    private final static String databasePassword = "mot38_Rot";

    public static void createNewUser() {
        Scanner input = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(url, user, databasePassword);
             Statement statement = connection.createStatement();
        ) {
            try {
                System.out.println("Please enter a username:");
                String username = input.nextLine();
                System.out.println("Please enter your password: ");
                String password = input.nextLine();
                System.out.println("What is your name?");
                String name = input.nextLine();
                System.out.println("What is your email? ");
                String emailAddress = input.nextLine();
                System.out.println("What is you address?");
                String address = input.nextLine();
                System.out.println("What is your phoneNumber number? ");
                String phoneNumber = input.nextLine();
                System.out.println("If you are a customer, enter 1. If you are an owner, enter 2");

                String typeOfUser = " ";
                if(input.nextInt() == 1){
                    typeOfUser = "customer";
                }else if(input.nextInt() == 2) {
                    typeOfUser = "owner";
                }

                statement.executeUpdate("insert into users values ('" + name + "', '" + emailAddress + "','" + address + "','" + phoneNumber + "','" + username + "','" + password + "','" + typeOfUser + "');");
                System.out.println("\nCongratulations! You have created your profile! You will now be redirected to the store.\n");
                loginUser(username, password);

            } catch (SQLException sqle) {
                System.out.println("Could not find user " + sqle);
                System.out.println("createuser");
            }

        } catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public static void loginUser(String username, String password){

        ResultSet result;
        String userType = null;
        String customer = "customer";

        try (Connection connection = DriverManager.getConnection(url, user, databasePassword);
             Statement statement = connection.createStatement();
        ) {
            try {
                result = statement.executeQuery(
                        "select type_of_user from users where username = ('" + username + "') and password = ('" + password + "')");
                result.next();
                userType = result.getString("type_of_user");

                if(userType.equals(customer)){ //if it's a customer we show certain menu
                    System.out.println("Welcome Customer! \n");
                    Consumer c = new Consumer(statement,connection, username);
                    c.consumerMenu();
                } else { // owner/manager // if it's a manager we show another menu
                    System.out.println("Welcome Owner! \n");
                    Owner o = new Owner(statement, connection, username);
                    o.ownerMenu();
                }

            } catch (SQLException sqle) {
                System.out.println("Could not find user " + sqle);
                System.out.println("loginuser");
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

        switch (input.nextInt()){
            case 1:
                System.out.println("Enter Username: \n");
                String userName = login.nextLine();
                System.out.println("Enter Password: \n");
                String password = login.nextLine();
                loginUser(userName, password);
                break;
            case 2:
                createNewUser();
                break;

        }
//        if (input.nextInt() == 1){
//
//        }else if (input.nextInt() == 2){
//
//        }
    }
}
