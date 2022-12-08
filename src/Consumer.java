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
                searchBook();
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

        }/*
        if(select.nextInt() == 1){
            searchBook();
        }else if(select.nextInt() == 2){
            buyBook();
        }else if(select.nextInt() == 3){
            checkOrders();
        }else if(select.nextInt() == 4){
            viewCart(username);
        }else if(select.nextInt() == 5){
            System.exit(0);
        }*/
    }

    public void searchBook(){

        System.out.println("Select the way you would like to search?");
        System.out.println("Search by Title, enter 1 ");
        System.out.println("Search by ISBN, enter 2 ");
        System.out.println("Search by Author, enter 3 ");
        System.out.println("Search by Genre, enter 4 ");
        System.out.println("Return to previous menu, enter 5 ");

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
                consumerMenu();
                break;
        }
        /*
        if(select.nextInt() == 1){
            System.out.println("What is the title you are looking for?");
            searchByTitle(input.nextLine());
        }else if(select.nextInt() == 2){
            System.out.println("What is the ISBN Number (7 digits) ?");
            searchByISBN(select.nextInt());
        }else if(select.nextInt() == 3){
            System.out.println("What is the author you are looking for?");
            searchByAuthor(input.nextLine());
        }else if(select.nextInt() == 4){
            System.out.println("What is the genre you are looking for?");
            searchByGenre(input.nextLine());
        }else{
            consumerMenu();
        }*/
    }

    public void searchByTitle(String bookTitle){
        try  {
            result = statement.executeQuery(
                    "select book.ISBN, book.book_name, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book.book_name like '%" + bookTitle + "%';");

            while(result.next()) {
                System.out.println("Book name: " + result.getString("book_name"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
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

    public void searchByISBN(int ISBN){

        try  {
            result = statement.executeQuery(
                    "select book.book_name, book.ISBN, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book.ISBN like '%" + ISBN + "%' ;");

            while(result.next()) {
                System.out.println("Book name: " + result.getString("book_name"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
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
                System.out.println("Book Title: " + result.getString("book_name"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
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
                System.out.println("Book Title: " + result.getString("book_title"));
                System.out.println("ISBN: " + result.getString("isbn"));
                System.out.println("Author: " + result.getString("author_name"));
                System.out.println("Genre: " + result.getString("genre"));
                System.out.println("Number of Pages: " + result.getString("num_pages"));
                System.out.println("Price: $" + result.getString("price") + "\n");
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
            searchBook();
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
                    removeFromCart(bookTitle);
                    break;

            }
            /*
            if(select.nextInt() == 1){
                createOrder(userName);
            }else if(select.nextInt() == 2){
                consumerMenu();
            }else if(select.nextInt() == 3){
                System.out.println("What is the book title of the book you would like to remove?");
                String bookTitle = input.nextLine();
                removeFromCart(bookTitle);
            }*/

        }  catch (SQLException sqle) {
            System.out.println("You have not added anything into your cart." + sqle);
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
    }

    public void removeFromCart(String bookName){
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
            result = statement.executeQuery("select * from cart where username = ('" + userName + "') ;");
            while (result.next()){
                totalPrice += result.getDouble("total_price") * result.getInt("quantity");
                temp1.add(result.getString("ISBN")); //store the ISBN in a temp array.
                temp2.add(result.getInt("quantity")); //store the quantity of the ISBN book in the same index for temp use.
            }
            amountSold(temp1, temp2); //change the amount sold


            System.out.println("Would you like to use a different shipping and billing address for your order? ");
            System.out.println("Yes, enter 1");
            System.out.println("No, enter 2");

            if(select.nextInt() == 1){
                System.out.println("What is address you would like us to send it too?");
                newA = input.nextLine();
                newAddress = true;
            }

            statement.executeUpdate("insert into orders values ('" + userName + "', '" + orderNumGenerated + "');"); //need to do this first because of order_num foreign key
            statement.executeUpdate("insert into orders_data values ( '" + orderNumGenerated + "'," + totalPrice + ",'" + trackingNumGenerated + "','" + dtf.format(now) + "');");
            statement.executeUpdate("delete from cart;"); //after creating the order, the checkout_cart rows can be deleted

            System.out.println("Order Created Successfully. Here is your order details:");
            System.out.println("Here is your order Number: " + orderNumGenerated);
            System.out.println("Here is your tracking number: " + trackingNumGenerated);
            System.out.println("Date Order was created: " + dtf.format(now) );                 //add date
            /*
            if(newAddress) {
                System.out.println(company + " will be shipping your books to this address: " + newA);
            } else{
                result = statement.executeQuery("select address from users where username = '" + userName + "';");
                if(result.next()){
                    System.out.println(company + " will be shipping your books to this address: " + result.getString("address"));
                }

            }*/
            System.out.println("The total price of your order is: $" + totalPrice + "\n");

        }  catch (SQLException sqle) {
            System.out.println("You have not added anything into your cart." + sqle);
            consumerMenu();
        }  catch (Exception sqle){
            System.out.println("Exception: " + sqle);
        }
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

    public void amountSold (ArrayList<String> ISBN,ArrayList<Integer> quantity){ //change the amount sold
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
