create table book_data
	(book_name		varchar(20),
	 author_name	varchar(20),
	 genre			varchar(20),
	 primary key (book_name)
	);
	
create table users
	(name			varchar(20) NOT NULL, 
	 email_address	varchar(30) NOT NULL,
	 address		varchar(30),
	 phone_number	varchar(12),
	 username		varchar(20) NOT NULL, 
	 password		varchar(20) NOT NULL,
	 type_of_user	varchar(20) NOT NULL,
	 primary key (username, email_address)
	);

create table publisher
	(publisher_name		varchar(20), 
	 p_id			varchar(7) NOT NULL, 
	 email_address	varchar(30) NOT NULL,
	 address		varchar(30),
	 phone_number	varchar(12),
	 bank_account_number	numeric(7,0) NOT NULL,
	 primary key (p_id)
	);
	
create table book
	(ISBN			varchar(7) NOT NULL,
	 book_name		varchar(20),
	 num_pages		numeric(3,0),
	 price			numeric(5,2),
	 p_id			varchar(7),
	 amount_sold		numeric(5,0),
	 publisher_fee		numeric(5,2),
	 primary key (ISBN),
	 foreign key (p_id) references publisher(p_id),
	 foreign key (book_name) references book_data(book_name)
	);

create table inventory
	(ISBN				varchar(20), 
	 book_name			varchar(20), 
	 book_quantity			numeric(5,0), 
	 book_threshold			numeric(5,0),
	 primary key (ISBN),
	 foreign key (ISBN) references book(ISBN)
	);
	
create table orders
	(username		varchar(20),
	 order_number	varchar(15) NOT NULL,
	 primary key (order_number), 
	 foreign key (username) references users(username)
	);

create table orders_data
	(order_number		varchar(15) NOT NULL,
	 order_total		numeric(7,2),
	 tracking_number	varchar(15),
	 date			DATE,
	 primary key (order_number), 
	 foreign key (order_number) references orders(order_number)
	);
	
create table cart
	(ISBN 		varchar(20),
	 book_name	varchar(15),
	 username	varchar(20),
	 total_price    numeric(7,2),
	 quantity	numeric(5,0),
	 primary key(ISBN, username), 
	 foreign key (username) references users(username),
	 foreign key (ISBN) references book(ISBN)
	);
	
                