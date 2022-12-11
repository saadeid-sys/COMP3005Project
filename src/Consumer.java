import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.sql.*;
public class Consumer extends Users{
    Statement statement;
    String username;
    Connection connection;
    ResultSet result = null;
    Scanner input, select;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
    LocalDateTime now = LocalDateTime.now();

    public Consumer(Statement statement, Connection connection, String username){
        this.statement = statement;
        this.connection = connection;
        this.username = username;
        input = new Scanner(System.in);
        select = new Scanner(System.in);
    }

    public void consumerMenu(){

        System.out.println("Select an option from the following menu:");
        System.out.println("Search a book, enter 1 ");
        System.out.println("Buy a book, enter 2 ");
        System.out.println("Check Orders, enter 3 ");
        System.out.println("Checkout Cart enter, 4 ");
        System.out.println("Exit, enter 5 ");

        switch (select.nextInt()){
            case 1:
                QueryBook();
                break;
            case 2:
                buyBook();
                break;
            case 3:
                checkOrders();
                break;
            case 4:
                viewCart(username);
                break;
            case 5:
                System.exit(0);

        }
    }

    public void QueryBook(){

        System.out.println("How would you like to query for a book?");
        System.out.println("Query by Title, enter 1 ");
        System.out.println("Query by ISBN, enter 2 ");
        System.out.println("Query by Author, enter 3 ");
        System.out.println("Query by Genre, enter 4 ");
        System.out.println("Return to previous menu, enter 5 ");

        switch (select.nextInt()){
            case 1:
                System.out.println("What is the title you are looking for?");
                queryByTitle(input.nextLine());
                break;
            case 2:
                System.out.println("What is the 7 digit ISBN Number?");
                searchByISBN(select.nextInt());
                break;
            case 3:
                System.out.println("What is the author of the book you are looking for?");
                searchByAuthor(input.nextLine());
                break;
            case 4:
                System.out.println("What is the genre you are looking for?");
                searchByGenre(input.nextLine());
                break;
            case 5:
                consumerMenu();
                break;
        }
    }

    public void queryByTitle(String bookTitle){
        try  {
            result = statement.executeQuery(
                    "select book.ISBN, book.book_name, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book.book_name like '%" + bookTitle + "%';");

            while(result.next()) {
                printResultSet(result);
//                System.out.println("ISBN: " + result.getString("isbn"));
//                System.out.println("Book name: " + result.getString("book_name"));
//                System.out.println("Author: " + result.getString("author_name"));
//                System.out.println("Genre: " + result.getString("genre"));
//                System.out.println("Number of Pages: " + result.getString("num_pages"));
//                System.out.println("Price: $" + result.getString("price") + "\n");
            }

            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes, enter 1");
            System.out.println("No (continue searching), enter 2");
            System.out.println("Return to main menu, enter 3");
            selectOption(select.nextInt());
        }
        catch (SQLException sqle) {
            System.out.println("Could not find book " + bookTitle + sqle);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    private void printResultSet(ResultSet result) throws SQLException {
        System.out.println("ISBN: " + result.getString("isbn"));
        System.out.println("Book name: " + result.getString("book_name"));
        System.out.println("Author: " + result.getString("author_name"));
        System.out.println("Genre: " + result.getString("genre"));
        System.out.println("Number of Pages: " + result.getString("num_pages"));
        System.out.println("Price: $" + result.getString("price") + "\n");
    }

    public void searchByISBN(int ISBN){

        try  {
            result = statement.executeQuery(
                    "select book.book_name, book.ISBN, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book.ISBN like '%" + ISBN + "%' ;");

            while(result.next()) {
                printResultSet(result);
//                System.out.println("Book name: " + result.getString("book_name"));
//                System.out.println("ISBN: " + result.getString("isbn"));
//                System.out.println("Author: " + result.getString("author_name"));
//                System.out.println("Genre: " + result.getString("genre"));
//                System.out.println("Number of Pages: " + result.getString("num_pages"));
//                System.out.println("Price: $" + result.getString("price") + "\n");
            }
            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes, enter 1");
            System.out.println("No (continue searching), enter 2");
            System.out.println("Return to main menu, enter 3");
            selectOption(select.nextInt());
        }
        catch (SQLException sqle) {
            System.out.println("Could not find book with ISBN number: " + ISBN);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void searchByAuthor(String author){

        try  {
            result = statement.executeQuery(
                    "select book.ISBN, book.book_name, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_name.book_name where book_data.author_name like '%" + author + "%' ;");

            while(result.next()) {
                printResultSet(result);
//                System.out.println("Book Title: " + result.getString("book_name"));
//                System.out.println("ISBN: " + result.getString("isbn"));
//                System.out.println("Author: " + result.getString("author_name"));
//                System.out.println("Genre: " + result.getString("genre"));
//                System.out.println("Number of Pages: " + result.getString("num_pages"));
//                System.out.println("Price: $" + result.getString("price") + "\n");
            }
            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes, enter 1");
            System.out.println("No (continue searching), enter 2");
            System.out.println("Return to main menu, enter 3");
            selectOption(select.nextInt());
        }
        catch (SQLException sqle) {
            System.out.println("Could not find books by: " + author);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void searchByGenre(String genre){

        try  {
            result = statement.executeQuery(
                    "select book.book_name, book.ISBN, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book_data.genre = ('" + genre + "') ;");

            while(result.next()) {
                printResultSet(result);
//                System.out.println("Book Title: " + result.getString("book_title"));
//                System.out.println("ISBN: " + result.getString("isbn"));
//                System.out.println("Author: " + result.getString("author_name"));
//                System.out.println("Genre: " + result.getString("genre"));
//                System.out.println("Number of Pages: " + result.getString("num_pages"));
//                System.out.println("Price: $" + result.getString("price") + "\n");
            }

            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes, enter 1");
            System.out.println("No (continue searching), enter 2");
            System.out.println("Return to main menu, enter 3");
            selectOption(select.nextInt());
        }
        catch (SQLException sqle) {
            System.out.println("Could not find books with the genre: " + genre);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void selectOption(int option){
        if (option == 1){
            buyBook();
        }else if (option == 2){
            QueryBook();
        }else if (option == 3){
            consumerMenu();
        }
    }

    public void buyBook() {
        System.out.println("What is the ISBN of the book?");
        Scanner input = new Scanner(System.in);
        String bookISBN = input.nextLine();
        System.out.println("How many would you like to purchase?");
        int quantity = input.nextInt();

        try {
            result = statement.executeQuery("select ISBN from book where ISBN = '" + bookISBN + "';"); //check to see if the book exists
            if(result.next()){
                result = statement.executeQuery("select book_quantity from inventory where ISBN = '" + bookISBN + "'");
                if(result.next()){
                    int quan = result.getInt("book_quantity");

                    if(quantity <= quan && quan != 0){ //then we can buy the book
                        result = statement.executeQuery("select ISBN, book_name, price from book where ISBN = '" + bookISBN + "';");
                        if(result.next()) {
                            statement.executeUpdate("insert into cart values ('" + bookISBN + "', '" + result.getString("book_name") + "', '" + username +  "' ," + result.getDouble("price") + ", " + quantity + ")");
                            System.out.println("Book added to cart!");
                        }
                    } else {
                        System.out.println("We do not have " + quantity + " of this book. ");
                        System.out.println("We only have " + quan + " currently available.");
                        buyBook();
                    }
                }
            }else{
                System.out.println("This book does not exist. Please search for another book");
                buyBook();
            }

            consumerMenu();

        } catch (SQLException sqle) {
            System.out.println("Could not purchase book" + sqle.getMessage());
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void checkOrders(){

        try{
            result = statement.executeQuery("select * from orders right join orders_data on orders.order_number = orders_data.order_number and username = ('" + username + "') ;");

            System.out.println("Orders for user: " + username);
            while (result.next()){
                System.out.println("Order Number: " + result.getString("order_num"));
                System.out.println("Order Total: $"  + result.getString("order_total"));
                System.out.println("Order Created: " + result.getString("date") + "\n");
            }

        }  catch (SQLException sqle) {
            System.out.println("Could not find order");
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }

    }

    public void viewCart(String userName){
        try{
            result = statement.executeQuery("select * from cart where username = ('" + userName + "') ;");

            System.out.println("\nCart: ");
            while (result.next()){
                System.out.println("Book ISBN: " + result.getString("ISBN"));
                System.out.println("Book name: "  + result.getString("book_name"));
                System.out.println("Price per copy: $" + result.getString("total_price"));
                System.out.println("Amount of Copies: " + result.getString("quantity") + "\n");
            }

            System.out.println("What would you like to do?");
            System.out.println("Create Order, enter 1");
            System.out.println("Continue shopping, enter 2");
            System.out.println("Remove an Item from Cart, enter 3");

            switch(select.nextInt()){
                case 1:
                    createOrder(userName);
                case 2:
                    consumerMenu();
                    break;
                case 3:
                    System.out.println("What is the book title of the book you would like to remove?");
                    String bookTitle = input.nextLine();
                    removeBookFromCart(bookTitle);
                    break;

            }

        }  catch (SQLException sqle) {
            System.out.println("You have not added anything into your cart." + sqle);
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void removeBookFromCart(String bookName){
        try{
            statement.executeUpdate("delete from cart where book_name = '" + bookName + "';");
            viewCart(username);

        }  catch (SQLException sqle) {
            System.out.println("Could not remove the book." +sqle);
            viewCart(username);
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void createOrder(String userName){
        Random rand = new Random();
        int orderNumGenerated = rand.nextInt(10000);
        int trackingNumGenerated = rand.nextInt(10000);
        double totalPrice = 0;
        boolean newAddress = false;
        String newA = null;
        ArrayList <String> temp1 = new ArrayList<>();
        ArrayList <Integer> temp2 = new ArrayList<>();

        try{
            result = statement.executeQuery("select * from cart where username = ('" + userName + "') ;");
            while (result.next()){
                totalPrice += result.getDouble("total_price") * result.getInt("quantity");
                temp1.add(result.getString("ISBN")); //store the ISBN in a temp array.
                temp2.add(result.getInt("quantity")); //store the quantity of the ISBN book in the same index for temp use.
            }
            amountOfBooksSold(temp1, temp2);

            System.out.println("Would you like to use a different shipping and billing address for your order? ");
            System.out.println("Yes, enter 1");
            System.out.println("No, enter 2");

            if(select.nextInt() == 1){
                System.out.println("What is address you would like us to send it too?");
                newA = input.nextLine();
                newAddress = true;
            }

            statement.executeUpdate("insert into orders values ('" + userName + "', '" + orderNumGenerated + "');");
            statement.executeUpdate("insert into orders_data values ( '" + orderNumGenerated + "'," + totalPrice + ",'" + trackingNumGenerated + "','" + dtf.format(now) + "');");
            statement.executeUpdate("delete from cart;");

            printOrderDetail(orderNumGenerated, trackingNumGenerated, totalPrice);

        }  catch (SQLException sqle) {
            System.out.println("You have not added anything into your cart." + sqle);
            consumerMenu();
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    private void printOrderDetail(int orderNumGenerated, int trackingNumGenerated, double totalPrice) {
        System.out.println("Order Created Successfully. Here is your order details:");
        System.out.println("Here is your order Number: " + orderNumGenerated);
        System.out.println("Here is your tracking number: " + trackingNumGenerated);
        System.out.println("Date Order was created: " + dtf.format(now) );
        System.out.println("The total price of your order is: $" + totalPrice + "\n");
    }

    public void payPublisher (String ISBN, int num_books){
        try  {
            result = statement.executeQuery("select publisher_fee, p_id from book where ISBN = '" + ISBN + "';");

            if(result.next()) {
                double fee = result.getDouble("publisher_fee");
                double total = fee * (double) num_books;
                ResultSet result1 = statement.executeQuery("select bank_account_number from publisher where p_id = '" + result.getString("p_id") + "';");
                if(result1.next()) {
                    int accountNum = result1.getInt("bank_account_number");
                    System.out.println("\n*Publisher with account number '" + accountNum + "' was paid $" + total + "*\n"); //this is displaying to show functionality
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Could not pay publisher!" +sqle);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void amountOfBooksSold(ArrayList<String> ISBN, ArrayList<Integer> quantity){
        try  {
            for(int i = 0; i < ISBN.size(); i++){
                ResultSet res = statement.executeQuery("select amount_sold, publisher_fee from book where ISBN = '" + ISBN.get(i) + "';");
                if(res.next()){
                    int amountSold = res.getInt("amount_sold") + quantity.get(i);
                    statement.executeUpdate("update book set amount_sold = " + amountSold + " where ISBN = '" + ISBN.get(i) + "';");
                    payPublisher(ISBN.get(i), quantity.get(i));
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
