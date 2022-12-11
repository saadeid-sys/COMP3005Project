--Creating a new user
insert into users values ('name ',' address ',' email_address ', ' phone_number ','username ','password ','type_of_user');

-- Using the username and password, is user a customer or owner
select type from users where username = (' username ') and password = (' password '); 

--Owner Queries
-- Add books to the Store
select * from book where ISBN = ' ISBN ';
select book_quantity from inventory where ISBN = 'ISBN ';
update inventory set book_quantity = ' quantity ' where ISBN = 'ISBN ';

--Add book steps:
--Add new publisher
--Add book details into book_data
--Add book into the book relation
--Add book into inventory
insert into publisher values ('pub_name ', 'p_id' ,'address', ' email address','phone_Num ', bankAccountNumber );
insert into book_data values (' Booktitle ',' author ', ' genre ');
insert into book values (' Booktitle ', ' newIsbn ',numPages ,price , ' pub_id ',0 ,pub_fee );
insert into inventory values ('Booktitle ', 'newIsbn', amount ,'threshold');


-- remove books: this will only remove from the inventory and chnage the quantity to the specified amount
select book_quantity from inventory where ISBN = ' ISBN ';
update inventory set book_quantity =  numBooks ;

-- generate reports
------- Sales by author
select * from book natural join (select * from book_data where author_name = ' author ') as table2;
------- Sales by dates (between two dates given)
select order_total from orders_data where date >= ' fD ' and date <= ' sD ';


--Customer Queries
--Search by Title
select book.ISBN, book.book_name, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book.book_name like '%" + bookTitle + "%';

--Search by ISBN
select book.book_name, book.ISBN, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book.ISBN like '%" + ISBN + "%' ;

--Search by Author
select book.ISBN, book.book_name, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_name.book_name where book_data.author_name like '%" + author + "%' ;

--Search by Genre
select book.book_name, book.ISBN, book_data.author_name, book_data.genre, book.num_pages, book.price from book full outer join book_data on book.book_name = book_data.book_name where book_data.genre = ('" + genre + "') ;");

-- buyBook
select ISBN from book where ISBN = 'bookISBN ';
select quantity from inventory where ISBN = ' bookISBN ';
select book_name, ISBN, price from book where ISBN = 'bookISBN ';
insert into checkout_cart values ('bookISBN ', 'book_name','userName', price , quantity);

--View cart
select * from cart where username = ('username') ;

--remove a specific book from cart
delete from cart where book_name = ' book ';

-- create order
select * from cart where username = ('username') ;
insert into orders values ('orderNumGenerated ', ' username ');
insert into orders_data values ( 'orderNumGenerated' , totalPrice ,' trackingNumGenerated ', 'date');
delete from cart;

--check orders
select * from orders right join orders_data on orders.order_number = orders_data.order_number and username = ('username');

-- payPublisher
select publisher_fee, publisher_id from book where ISBN = ' ISBN ';
select bank_account_number from publisher where publisher_id = 'publisher_id';

--amount sold
select amount_sold, publisher_fee from book where ISBN = ' ISBN ';
update book set amount_sold = " + amountSold + " where ISBN = ' ISBN '; --update how many of that book was sold.









