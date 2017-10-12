#database-Assignment
Author: Mudasar Ahmad, version 1.0

#IMPORTANT TO READ THIS BEFORE TESTING APPLICATION

** Basic information **

**You should have basic knowledge about MySql to handle .txt files that sends to the program that contain metadata and data**

**1** First line -> Table name

**2** Second line -> column name with '/' after each column name to separate them. eg = id/name/birthyear/mail (no spaces before and after '/')

**3** Third line -> Data types are written for each column above and are also separated by '/'. eg = INT (11)/VARCHAR (255)/date/VARCHAR(255) If you want the id to be updated automatically, you can set the first data type like = INT(11) AUTO_INCREMENT, the program will handle this for you (no spaces before and after '/')

**4** Fourth line -> the primary but need to be equals to one of the column in second line, eg = id, If you want more, you can only separate them by comma, for example = id,name

**5** Fifth line -> Here you can insert anything, this is to distinguish metadata from common data, i have seperated these with this line = --------------------<<SEPRATOR BETWEEN METADATA AND DATA>>--------------------

**6** Sixth line and so on -> Here you enter data to fill in the tables, here you also have to save with '/'. If a table contains = id/name/birthyear/email then you write from sixth line = 1/Ola Nordmann/1986-01-01/olanordmann@hotmail.com, if a column such as id has received the data type auto_increment, enter all except '1', ie = Ola Nordmann/1986-01-01/olanordmann@hotmail.com

**7** To delete, enter or update a row, change this only on .txt files. For example, to delete Ola Nordmann from the list, remove the line and run the program again

**8** To avoid duplicates, at least one column should be unique (UNIQUE) or a composite primary key

**9** Before starting the program, download xampp from https://www.apachefriends.org/download.html or one you prefer to use and turn it on before run Program and testes

**10** Go to database.properties file in resources folder and set your hostName, dbName to one you prefer to name your database, dont need to check if it exist or not, application will handle it, then set your userName and passWord to your own and which port you want to use, default is 3306 or 3307

**11** Check in 'helpMaterials', in this folder you will find 'files.txt' here you enter the names of the files you want to read for creating tables and rows. It is important that the files to read are only ".txt" files and that they are in the 'inputFiles' folder

**12** Now run the application and enjoy using the program

#Functionality

-Create database

-Create tables

-Drop tables

-Insert data from files in inputFiles folder

-Refresh tables (This option is whenever you have updated the data in .txt files, recommended to refresh and insert again to update the database)

-Show all table names

-Get metadata from each table

-Get data from each table

-Find number of rows in each table

-Find any result from any table

#Things that could be better
- Give me feedback what i can do better

#Testing 
-To run these test you need to have your Apache server on eg. xampp

-Go into folder src/test/resources and find this file -> test-DB-right.properties

-Here you will find: hostName dbName userName passWord port

-Set these to your own settings, but the dbName you choose, you need to manually create a database equals to that one in properties file in your database before run testes