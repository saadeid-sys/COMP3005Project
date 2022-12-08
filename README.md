# COMP3005Project
The Look Inna Book bookstore application was implemented using Java, the postrgres database application, and JDBC server to connect to the database.

# What you need to run the program
- The project files
- The JDBC jar file

In order to run the Project on your machine, you must download the JDBC jar file found on the postgres website(found here: https://jdbc.postgresql.org/download/) and added it to the project. The main function can be found in main.java. Before running the main program, you must change the url, and databasePassword on line 6 and 8 in main.java so you can connect to the postegres database.

A DDL.sql file is included in the SQL folder that must be run before executing the main program to set up the database with the reqiured tables, attrirbutes, and types.
A SampleData.sql is also included in the SQL folder that will populate the bookstore with data such as books, and publishers.
