# Vehicle Rental System with GUI and Database


# Project Description
The Vehicle Rental Management System is a Java-based application that simplifies the daily operations of a vehicle rental business. Built with Java, Swing for the GUI, and Microsoft SQL Server for database, the system allows admins to manage vehicles, customers, reservations, and maintenance schedules through an intuitive interface.

# Group Members
- Moreno, Czar Serafin 0.
- Pulido, Keziah B.
- Rivera, Carl Adrian C.
- Vargas, Ashley Nicole C.
- Nicol, James F.

# Features and Functionalities
- Login System
- User Account for Renting Vehicles and Showing Vehicles Available.
- Admin Account for Managing Bookings, Vehicles, and User Accounts.
- Sales Report to track Sales made between two dates.

# Technologies Used
- Java JDK 23
- Java Swing
- Microsoft SQL Server 
- JDBC for Database Connection 
- Netbeans for IDE
- Git for Version Control

# Installation and Setup Instructions
- Install Java JDK 23 here https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html
- Install Microsoft SQL Server Express here https://www.microsoft.com/en-us/sql-server/sql-server-downloads
- Download SQL Server JDBC here https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16
- Steps to Set Up the database:
    - Install SQL Server Express and create an account with SQL Server Authentication. 
    - In creating an account with SQL Server Authentication, set the username to admin and the password to admin456 or else, you might need to tamper the source code so you can put your own credentials there.
    - After Setting Up the SQL Server, run the SQL File provided on the repository called vRentalSystemDB.sql which can be found in database folder.
- Steps on how to run the program
    - First, open Netbeans and Create a new project.
    - After creating a project, download the src folder on the repository and copy all the contents there, such as package, java files and etc.
    - Right click on the project name you created and click clean.
    - After cleaning, you can go to the runner package, and run the main.java file to run the system.

# Database Configuration
- Database Name
    - vRentalSystemDB
- Table Structures
    - ACCOUNT
        -AccountID varchar(10) primary key not null
        -FName varchar(100) not null 
        -LName varchar(100) not null
        -DriverLicenseNo varchar(100) not null 
        -Email varchar(100) not null unique
        -PhoneNumber varchar(11)
        -AccountPassword varchar(100) not null
        -DateCreated date not null
        -AccountRole varchar(100) not null
        -AccountStatus varchar(100)
    - VEHICLES
        -VehicleID varchar(10) primary key not null 
        -VType varchar(100) not null 
        -Brand varchar(100) not null
        -Model varchar(100)not null
        -Color varchar(100)not null
        -LicensePlate varchar(100) not null
        -RentPrice decimal (10,2) not null 
        -VehicleStatus varchar(100)
    - RENTAL_DETAILS
        -RentalID varchar(10) primary key not null
        -AccountID varchar(10) not null
        -VehicleID varchar(10) not null 
        -PickupDate date not null 
        -ReturnDate date not null
        -BillingDate date 
        -BillAmount decimal (10,2) 
        -PaymentMethod varchar(100)
        -RentalStatus varchar(100)
        -constraint accFK foreign key(AccountID) references ACCOUNT(AccountID)
        -constraint vehFK foreign key(VehicleID) references VEHICLES(VehicleID)
    - MAINTENANCE
        -MaintenanceID varchar(10) primary key not null 
        -VehicleID varchar(10) not null 
        -MaintenanceDate date not null
        -MaintenanceStatus varchar(100)
        -MDescription varchar(255)
        -constraint mainFK foreign key(VehicleID) references VEHICLES(VehicleID)





