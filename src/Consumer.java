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
//    Scanner input = new Scanner(System.in); //scan for strings
//    Scanner select = new Scanner(System.in); //scan for ints
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
    LocalDateTime now = LocalDateTime.now();

    public Consumer(Statement statement, Connection connection, String username){
        this.statement = statement;
        this.connection = connection;
        this.username = username;
        input = new Scanner(System.in); //scan for strings
        select = new Scanner(System.in); //scan for ints
    }

    public void customerMenu(){

        System.out.println("Select an select: ");
        System.out.println("Search a book (1) ");
        System.out.println("Buy a book (2) ");
        System.out.println("Check Orders (3) ");
        System.out.println("Checkout Cart (4) ");
//        System.out.println("Exit (5)");

        if(select.nextInt() == 1){
            searchBook();
        }else if(select.nextInt() == 2){
            buyBook();
        }else if(select.nextInt() == 3){
            checkOrders();
        }else if(select.nextInt() == 4){
            checkoutCart(username);
        }
//        else if(select.nextInt() == 5){
//            System.exit(0);
//        }
    }

    public void searchBook(){

        System.out.println("How would you like to search?");
        System.out.println("Search by Title (1) ");
        System.out.println("Search by ISBN (2) ");
        System.out.println("Search by Author (3) ");
        System.out.println("Search by Genre (4) ");
        System.out.println("Return to previous menu (5) ");

        switch (select.nextInt()){
            case 1:
                System.out.println("What is the title you are looking for?");
                searchByTitle(input.nextLine());
                break;
            case 2:
                System.out.println("What is the ISBN Number (7 digits) ?");
                searchByISBN(select.nextInt());
                break;
            case 3:
                System.out.println("What is the author you are looking for?");
                searchByAuthor(input.nextLine());
                break;
            case 4:
                System.out.println("What is the genre you are looking for?");
                searchByGenre(input.nextLine());
                break;
            case 5:
                customerMenu();
                break;
        }
    }

    public void searchByTitle(String bookTitle){
        try  {
            result = statement.executeQuery(
                    "select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book.book_title like '%" + bookTitle + "%';");

            while(result.next()) {
                System.out.println("Book Title: " + result.getString("book_title"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
            }

            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes (1)");
            System.out.println("No, continue searching (2)");
            System.out.println("Return to main menu (3)");
            switch (select.nextInt()){
                case 1:
                    buyBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    customerMenu();
                    break;
            }
        }
        catch (SQLException sqle) {
            System.out.println("Could not find book " + bookTitle + sqle);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void searchByISBN(int ISBN){

        try  {
            result = statement.executeQuery(
                    "select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book.ISBN like '%" + ISBN + "%' ;");

            while(result.next()) {
                System.out.println("Book Title: " + result.getString("book_title"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
            }
            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes (1)");
            System.out.println("No, continue searching (2)");
            System.out.println("Return to main menu (3)");
            switch (select.nextInt()){
                case 1:
                    buyBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    customerMenu();
                    break;
            }
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
                    "select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book_info.author_name like '%" + author + "%' ;");

            while(result.next()) {
                System.out.println("Book Title: " + result.getString("book_title"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
            }
            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes (1)");
            System.out.println("No, continue searching (2)");
            System.out.println("Return to main menu (3)");
            switch (select.nextInt()){
                case 1:
                    buyBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    customerMenu();
                    break;
            }
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
                    "select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book_info.genre = ('" + genre + "') ;");

            while(result.next()) {
                System.out.println("Book Title: " + result.getString("book_title"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
            }
            System.out.println("Would you like to purchase a book?");
            System.out.println("Yes (1)");
            System.out.println("No, continue searching (2)");
            System.out.println("Return to main menu (3)");
            switch (select.nextInt()){
                case 1:
                    buyBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    customerMenu();
                    break;
            }
        }
        catch (SQLException sqle) {
            System.out.println("Could not find books with the genre: " + genre);
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void buyBook() {

        System.out.println("What is the book ISBN?");
        Scanner input = new Scanner(System.in);
        String bookISBN = input.nextLine();
        System.out.println("How many would you like to purchase?");
        int quantity = input.nextInt();


        try {

            result = statement.executeQuery("select ISBN from book where ISBN = '" + bookISBN + "';"); //check to see if the book exists
            if(result.next()){
                result = statement.executeQuery("select quantity from inventory where ISBN = '" + bookISBN + "'");
                if(result.next()){
                    int quan = result.getInt("quantity");

                    if(quantity <= quan && quan != 0){ //then we can buy the book
                        result = statement.executeQuery("select book_title, ISBN, price from book where ISBN = '" + bookISBN + "';");
                        if(result.next()) {
                            statement.executeUpdate("insert into checkout_cart values ('" + bookISBN + "', '" + result.getString("book_title") + "'," + result.getDouble("price") + ", " + quantity + ",'" + username + "')");
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

            customerMenu();

        } catch (SQLException sqle) {
            System.out.println("Could not purchase book" + sqle.getMessage());
        }
        catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void checkOrders(){

        try{
            result = statement.executeQuery("select * from orders right join orders_info on orders.order_num = orders_info.order_num and user_name = ('" + username + "') ;");

            System.out.println("Orders for user: " + username);
            while (result.next()){
                System.out.println("Order Number: " + result.getString("order_num"));
                System.out.println("Order Total: $"  + result.getString("order_total_price"));
                System.out.println("Tracking Number: " + result.getString("tracking_num"));
                System.out.println("Shipping Company: " + result.getString("shipping_company"));
                System.out.println("Order Created: " + result.getString("date") + "\n");
            }

        }  catch (SQLException sqle) {
            System.out.println("Could not find order");
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }

    }

    public void checkoutCart(String userName){
        try{
            result = statement.executeQuery("select * from checkout_cart where user_name = ('" + userName + "') ;");

            System.out.println("\nCart: ");
            while (result.next()){
                System.out.println("Book ISBN: " + result.getString("ISBN"));
                System.out.println("Book Title: "  + result.getString("book_title"));
                System.out.println("Price per copy: $" + result.getString("price"));
                System.out.println("Amount of Copies: " + result.getString("quantity") + "\n");
            }

            System.out.println("What would you like to do?");
            System.out.println("Create Order (1) ");
            System.out.println("Continue shopping (2)");
            System.out.println("Remove an Item from Cart (3)");

            switch (select.nextInt()){
                case 1:
                    createOrder(userName);
                case 2:
                    customerMenu();
                case 3:
                    System.out.println("What is the book title of the book you would like to remove?");
                    String bookTitle = input.nextLine();
                    removeFromCart(bookTitle);
                    break;
            }

        }  catch (SQLException sqle) {
            System.out.println("You have not added anything into your cart." + sqle);
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void removeFromCart(String book){
        try{
            statement.executeUpdate("delete from checkout_cart where book_title = '" + book + "';");
            checkoutCart(username);

        }  catch (SQLException sqle) {
            System.out.println("Could not remove the book." +sqle);
            checkoutCart(username);
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }

    }

    public void createOrder(String userName){
        ResultSet result1;
        Random rand = new Random();
        int orderNumGenerated = rand.nextInt(10000); //create a random order number
        int trackingNumGenerated = rand.nextInt(10000); //create a random tracking number
        String [] shipping_comp = {"Amazon", "FedEx", "UPS"}; // one of these three shipping companies will be randomly chosen to perform the shipping.
        int randomShipping = rand.nextInt(shipping_comp.length);
        String company = shipping_comp[randomShipping];
        double totalPrice = 0;
        boolean newAddress = false;
        String newA = null;
        ArrayList <String> temp1 = new ArrayList<>();
        ArrayList <Integer> temp2 = new ArrayList<>();

        try{
            result = statement.executeQuery("select * from checkout_cart where user_name = ('" + userName + "') ;");
            while (result.next()){
                totalPrice += result.getDouble("price") * result.getInt("quantity");
                temp1.add(result.getString("ISBN")); //store the ISBN in a temp array.
                temp2.add(result.getInt("quantity")); //store the quantity of the ISBN book in the same index for temp use.
            }
            amountSold(temp1, temp2); //change the amount sold


            System.out.println("Would you like to use a different shipping and billing address for your order? ");
            System.out.println("Yes (1)");
            System.out.println("No (2)");
            switch (select.nextInt()){
                case 1:
                    System.out.println("What is address you would like us to send it too?");
                    newA = input.nextLine();
                    newAddress = true;
                    break;
                case 2:
                    break;
            }
            statement.executeUpdate("insert into orders values ('" + orderNumGenerated  + "', '" + userName + "');"); //need to do this first because of order_num foreign key
            statement.executeUpdate("insert into orders_info values ( '" + orderNumGenerated + "'," + totalPrice + ",'" + trackingNumGenerated + "', ' " + company + "','"+ dtf.format(now) + "');");
            statement.executeUpdate("delete from checkout_cart;"); //after creating the order, the checkout_cart rows can be deleted

            System.out.println("Order Created Successfully. Here is your order details:");
            System.out.println("Here is your order Number: " + orderNumGenerated);
            System.out.println("Here is your tracking number: " + trackingNumGenerated);
            System.out.println("Date Order was created: " + dtf.format(now) );                 //add date
            if(newAddress) {
                System.out.println(company + " will be shipping your books to this address: " + newA);
            } else{
                result = statement.executeQuery("select address from users where user_name = '" + userName + "';");
                if(result.next()){
                    System.out.println(company + " will be shipping your books to this address: " + result.getString("address"));
                }

            }
            System.out.println("The total price of your order is: $" + totalPrice + "\n");

        }  catch (SQLException sqle) {
            System.out.println("You have not added anything into your cart." + sqle);
            customerMenu();
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void payPublisher (String ISBN, int num_books){

        ResultSet result1;
        double fee;
        double total;
        int accountNum;

        try  {
            result = statement.executeQuery("select publisher_fee, publisher_id from book where ISBN = '" + ISBN + "';");

            if(result.next()) {
                fee = result.getDouble("publisher_fee");
                total = fee * (double) num_books;
                result1 = statement.executeQuery("select bank_account_number from publisher where publisher_id = '" + result.getString("publisher_id") + "';");
                if(result1.next()) {
                    accountNum = result1.getInt("bank_account_number");
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

    public void amountSold (ArrayList<String> ISBN,ArrayList<Integer> quantity){ //change the amount sold
        ResultSet result1;

        try  {
            for(int i = 0; i < ISBN.size(); i++){
                result1 = statement.executeQuery("select amount_sold, publisher_fee from book where ISBN = '" + ISBN.get(i) + "';");
                if(result1.next()){
                    int amountSold = result1.getInt("amount_sold") + quantity.get(i);
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
