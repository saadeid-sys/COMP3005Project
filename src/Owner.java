import java.util.Scanner;
import java.sql.*;

public class Owner extends Users {
    Statement statement;
    Connection connection;
    String username;
    ResultSet result = null;
    Scanner input, select;

    public Owner(Statement statement,Connection connection, String username){
        this.statement = statement;
        this.connection = connection;
        this.username = username;
        input = new Scanner(System.in);
        select = new Scanner(System.in);
    }

    public void ownerMenu(){

        System.out.println("Select an select:");
        System.out.println("Add books, enter 1");
        System.out.println("Delete books, enter 2");
        System.out.println("Generate Reports, enter 3");

        switch (select.nextInt()){
            case 1:
                System.out.println("What is the ISBN of the book you want to add to the store?");
                int ISBN = input.nextInt();
                System.out.println("How many do you want to add?");
                int amount = input.nextInt();
                addBooks(ISBN, amount);
                break;
            case 2:
                System.out.println("What is its ISBN of the book you want to remove from the store?");
                ISBN = input.nextInt();
                System.out.println("How many do you want to remove? (Must be larger than 0) ");
                amount = input.nextInt();
                removeBooks(ISBN, amount);
                break;
            case 3:
                generateReports();
        }
    }

    public void addBooks(int ISBN, int numBooks){

        ResultSet res;

        try  {
            result = statement.executeQuery(
                    "select * from book where ISBN = '" + ISBN + "';");

            if(result.next()){
                res = statement.executeQuery("select book_quantity from inventory where ISBN = '" + ISBN + "';");
                if(res.next()){
                    int quantityInventory = res.getInt("book_quantity") + numBooks;
                    statement.executeUpdate("update inventory set book_quantity = '" + quantityInventory + "' where ISBN = '" + ISBN + "';");
                }
                //must then pay the publisher
                payPublisher(ISBN, numBooks);

            } else{
                System.out.println("The book doesn't exist. Please give some information on the book and the publisher:\n");
                System.out.println("\nWhat is the title of the book you want to add to the store? (*Required*)");
                input.nextLine();
                String title = input.nextLine();
                System.out.println("\nWhat is the ISBN of the book you want to add to the store (7 digits)?");
                int newIsbn = input.nextInt();
                input.nextLine();
                System.out.println("\nHow many pages does the book have?");
                int numPages = input.nextInt();
                input.nextLine();
                System.out.println("\nWho is the book's author?");
                String author = input.nextLine();
                System.out.println("\nWhat is the genre of the book?");
                String genre = input.nextLine();
                System.out.println("\nWhat will be the price of the book?");
                double price = input.nextDouble();
                System.out.println("\nHow many do you want to add?");
                int amount = input.nextInt();
                input.nextLine();
                System.out.println("\nHow many books must be sold before an automatic purchase for more is done (aka Threshold)?");
                int threshold = input.nextInt();
                input.nextLine();
                System.out.println("\nWhat is the publisher's name?");
                String pub_name = input.nextLine();
                System.out.println("\nWhat will be this publisher's 7 digit ID number be?");
                int pub_id = input.nextInt();
                System.out.println("\nWhat is the publisher's fee for this book?");
                double pub_fee = input.nextDouble();
                input.nextLine();
                System.out.println("\nWhat is the publisher's address?");
                String address = input.nextLine();
                System.out.println("\nWhat is the publisher's phone number?");
                String phoneNum = input.nextLine();
                System.out.println("\nWhat is the publisher's email address?");
                String email = input.nextLine();
                System.out.println("\nWhat is the publisher's 7 digit bank account number for payment purposes?");
                int bNum = input.nextInt();


                statement.executeUpdate("insert into publisher values ('" + pub_name + "'," + pub_id + ",'" + email + "','" + address+"', '"  + phoneNum + "'," + bNum + ");");
                statement.executeUpdate("insert into book_data values ('" + title + "','" + author + "', '" + genre + "');");
                statement.executeUpdate("insert into book values ('" + newIsbn + "', '" + title + "'," + numPages + "," + price + ", '" + pub_id + "'," + 0 + "," + pub_fee + ");" );
                statement.executeUpdate("insert into inventory values ('" + newIsbn + "', '"+title+"'," + amount + "," +threshold+");");
                payPublisher(newIsbn, amount);
                ownerMenu();
            }

        } catch (SQLException sqle) {
            System.out.println("Could not add book(s)" + sqle);
            ownerMenu();
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }


    }

    public void removeBooks(int ISBN, int numBooks){ //will only remove books from the inventory, therefore it can't be sold

        try{

            result = statement.executeQuery("select book_quantity from inventory where ISBN = '" + ISBN + "';");
            if(result.next()){
                if(result.getInt("book_quantity") >= numBooks) {
                    statement.executeUpdate("update inventory set book_quantity = " + numBooks + ";");
                } else{
                    statement.executeUpdate("update inventory set book_quantity = " + 0 + ";");
                }
            }
        }
        catch (SQLException sqle) {
            System.out.println("Could not remove book(s)" + sqle);
            ownerMenu();
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }

    }

    public void generateReports(){

        System.out.println("Which Report would you like to see?");
        System.out.println("Sales by Author, enter 1");
        System.out.println("Sales between date, enter 2");

        switch (select.nextInt()) {
            case 1:
                System.out.println("What is the Author's name?");
                String author = input.nextLine();
                salesByAuthor(author);
                break;
            case 2:
                System.out.println("Between which dates would you like to find the sales?");
                System.out.println("Please enter the first date (Format yyyy-mm-dd) ");
                String firstDate = input.nextLine();
                System.out.println("Please enter the second date (Format yyyy-mm-dd) ");
                String secondDate = input.nextLine();
                salesByDates(firstDate, secondDate);
                break;
        }
    }

    public void salesByAuthor(String author){ //same thing would apply to any fo the other attribute fo book

        double totalSales = 0;

        try  {
            result = statement.executeQuery("select * from book natural join (select * from book_data where author_name = '" + author + "') as table2");

            while (result.next()){
                totalSales += result.getDouble("price")*result.getInt("amount_sold");
            }

            System.out.println("The total sales for the author, " + author + " is $" + totalSales);
            ownerMenu();

        } catch (SQLException sqle) {
            System.out.println("Could not generate report" +sqle);
            ownerMenu();
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void salesByDates(String fD, String sD){

        double totalSales = 0;

        try  {

            result = statement.executeQuery("select order_total from orders_data where date >= '" + fD + "' and date <= '" + sD + "';");

            while (result.next()){
                totalSales += result.getDouble("order_total_price");
            }

            System.out.println("The Sales between " + fD + " and " + sD + " is $" + totalSales);
            ownerMenu();

        } catch (SQLException sqle) {
            System.out.println("Could not generate report" +sqle);
            ownerMenu();
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }

    }

    public void payPublisher (int ISBN, int num_books){

        ResultSet result1;
        double fee;
        double total;
        int accountNum;

        try  {
            result = statement.executeQuery("select publisher_fee, p_id from book where ISBN = '" + ISBN + "';");
            if(result.next()) {
                fee = result.getDouble("publisher_fee");
                total = fee * (double) num_books;
                result1 = statement.executeQuery("select bank_account_number from publisher where p_id = '" + result.getString("p_id") + "';");
                if(result1.next()) {
                    accountNum = result1.getInt("bank_account_number");
                    System.out.println("\n*Publisher with account number '" + accountNum + "' was paid $" + total + "*\n");
                }
            }

        } catch (SQLException sqle) {
            System.out.println("Could not pay publisher!" +sqle);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }
}
