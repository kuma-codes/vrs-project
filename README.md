# Vehicle Rental System with GUI and Database


## Project Description
The Vehicle Rental Management System is a Java-based application that is designed to simplify the daily operations of a vehicle rental business. Developed using Java and Swing for the graphical user interface (GUI) and Microsoft SQL Server for the database, this system enables admins to effectively manage vehicles, customers, reservations, and maintenance schedules through a user-friendly interface.

## Group Members
- Moreno, Czar Serafin 0.
- Pulido, Keziah B.
- Rivera, Carl Adrian C.
- Vargas, Ashley Nicole C.
- Nicol, James F.

## Features and Functionalities
- Login System
- User Account for Renting Vehicles and Showing Vehicles Available.
- Admin Account for Managing Bookings, Vehicles, and User Accounts.
- Sales Report to track Sales made between two dates.

## Technologies Used
- Java JDK 23
- Java Swing
- Microsoft SQL Server 
- JDBC for Database Connection 
- Netbeans for IDE
- Git for Version Control

## Installation and Setup Instructions
- Install Java JDK 23 here https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html
- Install Microsoft SQL Server Express here https://www.microsoft.com/en-us/sql-server/sql-server-downloads
- Download LGoodDatePicker here https://github.com/LGoodDatePicker/LGoodDatePicker
- Download SQL Server JDBC here https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver16
- Steps to Set Up the database:
    - Install SQL Server Express and create an account with SQL Server Authentication. 
    - In creating an account with SQL Server Authentication, set the username to admin and the password to admin456 or else, you might need to tamper the source code so you can put your own credentials there.
    - After Setting Up the SQL Server, run the SQL File provided on the repository called vRentalSystemDB.sql which can be found in database folder.
- Steps on how to run the program
    - First, open Netbeans and Create a new project.
    - After creating a project, download the src folder on the repository and copy all the contents there, such as package, java files and etc.
    - Extract the two libraries downloaded, LGoodDatePicker and SQL Server JDBC and add it to the Project library, if not, the system will not work as intended. to do this, just locate the "LGoodDatePicker-x.x.x.jar" and "mssql-jdbc-x.x.x.jre11.jar", after that right click on libraries folder on your project and click add JAR file then
    select the two jar files mentioned.
    - Right click on the project name you created and click clean.
    - After cleaning, you can go to the runner package, and run the main.java file to run the system.

## Database Configuration
- Database Name
    - vRentalSystemDB
- Table Structures
    - ACCOUNT
        - AccountID varchar(10) primary key not null
        - FName varchar(100) not null 
        - LName varchar(100) not null
        - DriverLicenseNo varchar(100) not null 
        - Email varchar(100) not null unique
        - PhoneNumber varchar(11)
        - AccountPassword varchar(100) not null
        - DateCreated date not null
        - AccountRole varchar(100) not null
        - AccountStatus varchar(100)
    - VEHICLES
        - VehicleID varchar(10) primary key not null 
        - VType varchar(100) not null 
        - Brand varchar(100) not null
        - Model varchar(100)not null
        - Color varchar(100)not null
        - LicensePlate varchar(100) not null
        - RentPrice decimal (10,2) not null 
        - VehicleStatus varchar(100)
    - RENTAL_DETAILS
        - RentalID varchar(10) primary key not null
        - AccountID varchar(10) not null
        - VehicleID varchar(10) not null 
        - PickupDate date not null 
        - ReturnDate date not null
        - BillingDate date 
        - BillAmount decimal (10,2) 
        - PaymentMethod varchar(100)
        - RentalStatus varchar(100)
        - constraint accFK foreign key(AccountID) references ACCOUNT(AccountID)
        - constraint vehFK foreign key(VehicleID) references VEHICLES(VehicleID)
    - MAINTENANCE
        - MaintenanceID varchar(10) primary key not null 
        - VehicleID varchar(10) not null 
        - MaintenanceDate date not null
        - MaintenanceStatus varchar(100)
        - MDescription varchar(255)
        - constraint mainFK foreign key(VehicleID) references VEHICLES(VehicleID)

## Usage Guide
-Instructions on how to use the system

    - To use the system, you must first create an admin account. In order to do that, just insert an account in ACCOUNT table in the sql file, with an account role of Admin, after that, go back to the system and login as an admin, that way, you will have full control over the system features. In the Booking Management module, you can view and approve bookings, prepare vehicles, and access sales reports. In the Vehicle Management module, you can add, edit, or remove vehicles and schedule their maintenance. Under User Management, you can view all users and remove accounts if necessary.

    - For users, you must first create an account before logging into the system. After logging in, you will be directed to the landing page where you can access the main features. Show Vehicle Available allows you to see which vehicles are currently available for rent. Rent a Vehicle lets you choose and book a vehicle you want to rent. Return Vehicle is used to return a vehicle you have previously rented. Show Booking Status lets you view the status and history of your bookings.

## Know Issues & Future Improvements
-Any known bugs or limitations
    - No Mobile App Version. The system does not yet have a standalone mobile app. Although it is still available from a mobile browser, neither the layout nor the functionality is optimized for use on smartphones or tablets, which could negatively impact overall user experience on smaller screens.

-Possible future enchancements
    - Mobile App Support.A mobile application of the system will enable users to use features in a more accessible manner with the use of smartphones or tablets. This will enhance user experience by making booking, vehicle return, and status checking easier while on the move.
    - User Ratings and Reviews. This will enable users to provide feedback by rating their rental experience as well as the condition of the vehicles. It improves service quality and gives valuable information to future renters and admins.
    - Admin Role. Access control will enable varying levels of admin users, including Super Admin, Booking Manager, and Vehicle Manager. Every role will have assigned privileges, assisting in arranging duties and enhancing system security
    - Thorough improvement of payment system, such as having partnered with known card companies to integrate it as our payment method.
    - Thorough improvement of returning the vehicle, we can add more precise return inspection to avoid not getting compensated if the vehicle is returned damaged.


