--User login information 
--Creating a new user
insert into users values ('name ',' address ',' email_address ', ' phone_number ','username ','password ','type_of_user');

-- Using the username and password, is user a customer or owner
select type from users where username = (' username ') and password = (' password '); 

--Owner Queries
-- Add books to the Store

select * from book where ISBN = ' ISBN ';
select quantity from inventory where ISBN = 'ISBN ';
update inventory set quantity = ' quantityInventory ' where ISBN = 'ISBN ';

-- add book if it doesn't exist

insert into publisher values ('publisher name ', 'p_id' ,'address', ' email address','phone Number ', bankAccountNumber ); --first must add the new publisher
insert into book_info values (' title ',' author ', ' genre ');		-- then add too book_info
insert into book values (' title ', ' newIsbn ',numPages ,price , ' pub_id ',0 ,pub_fee ); --then add to book
insert into inventory values ('title ', 'newIsbn','quantity' + amount ,'threshold'); -- then update the ivnentory

-- remove books: this will only remove from the inventory and chnage the quantity to the specified amount

select quantity from inventory where ISBN = ' ISBN ';
update inventory set quantity =  numBooks ;

-- generate reports
------- Sales by author
select * from book natural join (select * from book_info where author_name = ' author ');
------- Sales by dates (between two dates given)
select order_total_price from orders_info where date >= ' fD ' and date <= ' sD ';


--Customer Queries
--Search by Title
select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book.book_title like '% bookTitle %';

--Search by ISBN
select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book.ISBN like '% ISBN %' ;

--Search by Author
select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book_info.author_name like '% author %';

--Search by Genre
select book.book_title, book.ISBN, book_info.author_name, book_info.genre, book.num_pages, book.price from book full outer join book_info on book.book_title = book_info.book_title where book_info.genre = (' genre ') ;

-- buyBook
select ISBN from book where ISBN = 'bookISBN ';
select quantity from inventory where ISBN = ' bookISBN ';
select book_title, ISBN, price from book where ISBN = 'bookISBN ';
insert into checkout_cart values ('bookISBN ', 'book_title',price , quantity ,' userName');

--check orders
select * from orders right join orders_info on orders.order_num = orders_info.order_num and user_name = ('userName ') ;

--checkout_cart
select * from checkout_cart where user_name = ('userName ') ;

--remove from cart
delete from checkout_cart where book_title = ' book ';

-- create order
select * from checkout_cart where user_name = ('userName') ; --loop through the chechkout_cart rows
insert into orders values ('orderNumGenerated ', ' userName '); --insert new order into orders table
insert into orders_info values ( 'orderNumGenerated' , totalPrice ,' trackingNumGenerated ', ' company','date'); --insert the rest of the orders_info
delete from checkout_cart; --when order is created the checkout cart can be emptied.

-- payPublisher
select publisher_fee, publisher_id from book where ISBN = ' ISBN ';		-- find the amoutn the Publisher is owed for that book
select bank_account_number from publisher where publisher_id = 'publisher_id'; --pay to their bank account

--amount sold
select amount_sold, publisher_fee from book where ISBN = ' ISBN ';
update book set amount_sold = " + amountSold + " where ISBN = ' ISBN '; --update how many of that book was sold.









