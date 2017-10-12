#dbWesterdals
Author: Mudasar Ahmad, version 1.0

#
**IMPORTANT TO READ THIS BEFORE TESTING APPLICATION**

**1.** Information about the inputFiles-folder
- You should have basic knowledge about MySql to handle these files that contain metadata needed to create tables
- First line -> column name with '/' after each column name to separate them. eg = id / name / birthyear / mail
- Second line -> Data types are written for each column above and are also separated by '/'. eg = INT (11) / VARCHAR (255) / date / VARCHAR (255)
                If you want the id to be updated automatically, you can set the first data type like = INT (11) AUTO_INCREMENT, the program will handle this for you
- Third line -> which column in 2 line should contain the primary key, eg = id,
                 If you want more, you can only separate them by comma, for example = id, name
- Fourth line -> Here you can insert anything, this is to distinguish metadata from common data, you can save with a '/' or more '//////////' or a '-' or more ' -------- ', you can choose any character
- Fifth line and so on -> Here you enter data to fill in the tables, here you also have to save with '/'. If a table contains = id / name / birthyear / email then you write from sixth line = 1 / Ola Nordmann/1986-01-01/olanordmann@hotmail.com,
                          if a column such as id has received the data type auto_increment, enter all except 1 / ie = Ola Nordmann/1986-01-01/olanordmann@hotmail.com
- To delete, enter or update a row, change this only on .txt files. For example, to delete Ola Nordmann from the list, remove the line and run the program again
- To avoid duplicates, at least one column should be unique (UNIQUE) or a composite primary key

**2.** Before starting the program, 
download xampp from https://www.apachefriends.org/download.html 
or one you prefer to use

**3.** Go to database.properties file in resources folder and set your hostName, 
dbName to one you prefer to name your database, dont need to check if it exist or not, application will handle it,
then set your userName and passWord to your own and which port you want to use, default is 3306 or 3307

**4.** Now run the application and enjoy using the program

#Functionality
- Create database
- Create tables
- Insert data from files in inputFiles folder
- Show all table names
- Get metadata from each table
- Get data from each table
- Find number of rows in each table
- Find any result from any table


#Things that could be better
- insertDataIntoTable() in DBHandler.class could be a better method by avoiding SQL injection, it is vulnerable to this method. The method is designed to dynamically create tables, but adds the entire query as a string that made it difficult for me to post in the correct way, referring to this as it is really the way to do: https://www.owasp.org/index.php/SQL_Injection_Prevention_Cheat_Sheet
So I am very aware of that
- The connectTables() method in DBHandler.class could be improved by making them dynamic. I'm running regular queries here to connect to the tables. Please provide feedback on any payroll to dynamically link links between tables


#Testing
Go into folder src/test/resources and find this file -> test-DB-right.properties

Here you will find: hostName
                    dbName
                    userName
                    passWord
                    port
                    
Set these to your own settings, but the dbName you choose, you need to manually create a database name manually in your database
And also have your Apache server on when you are running these test

