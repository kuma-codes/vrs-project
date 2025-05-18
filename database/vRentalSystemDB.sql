--run the next line before doing anything else, do not use if you already have this database on your sql server
create database vRentalSystemDB

USE master
use vRentalSystemDB
drop database vRentalSystemDB

--this is the creation of the tables do this first before the alter table/adding constraints
create table ACCOUNT(
AccountID varchar(10) primary key not null,
FName varchar(100) not null, LName varchar(100) not null,
DriverLicenseNo varchar(100) not null, Email varchar(100) not null unique,PhoneNumber varchar(11),AccountPassword varchar(100) not null,
DateCreated date not null, AccountRole varchar(100) not null, AccountStatus varchar(100) 
)

create table RENTAL_DETAILS(
RentalID varchar(10) primary key not null, AccountID varchar(10) not null,
VehicleID varchar(10) not null, PickupDate date not null, ReturnDate date not null,
BillingDate date, BillAmount decimal (10,2), PaymentMethod varchar(100), RentalStatus varchar(100)
)

create table VEHICLES(
VehicleID varchar(10) primary key not null, VType varchar(100) not null, Brand varchar(100) not null,
Model varchar(100)not null, Color varchar(100)not null,LicensePlate varchar(100) not null, RentPrice decimal (10,2) not null, VehicleStatus varchar(100)
)

create table MAINTENANCE(
MaintenanceID varchar(10) primary key not null, VehicleID varchar(10) not null, MaintenanceDate date not null, MaintenanceStatus varchar(100),
MDescription varchar(255)
)

--adding constraints to the table
alter table RENTAL_DETAILS add constraint accFK foreign key(AccountID) references ACCOUNT(AccountID)
alter table RENTAL_DETAILS add constraint vehFK foreign key(VehicleID) references VEHICLES(VehicleID)
alter table MAINTENANCE add constraint mainFK foreign key(VehicleID) references VEHICLES(VehicleID
)

select * from ACCOUNT
delete from ACCOUNT
-- inserting database values

--Account Sample Data
INSERT INTO ACCOUNT (AccountID, FName, LName, DriverLicenseNo, Email, PhoneNumber, AccountPassword, DateCreated, AccountRole, AccountStatus)
VALUES
-- Admins
('U1','Czar','Moreno','M01-23-456789','czar@example.com','09178541236','vrsystem2025','2024-05-12','Admin','Not Renting'),
('U2','Keziah','Pulido','D05-21-123456','kez@example.com','09234561278','vrsystem2025','2024-05-12','Admin','Not Renting'),
('U3','Carl','Rivera','R12-22-987654','carl@example.com','09387456321','vrsystem2025','2024-05-12','Admin','Not Renting'),
('U4','Ashley','Vargas','M08-24-345678','ash@example.com','09129874563','vrsystem2025','2024-05-12','Admin','Not Renting'),
('U5','James','Nicol','S03-25-654321','james@example.com','09456321874','vrsystem2025','2024-05-12','Admin','Not Renting'),

--Users
('U6','Liam','Garcia','L02-19-123456','liamg@example.com','09234567891','user123','2024-11-26','User','Not Renting'),
('U7','Emma','Reyes','E04-20-987654','emmar@example.com','09123459876','user123','2024-09-17','User','Not Renting'),
('U8','Noah','Santos','N09-18-456789','noahs@example.com','09345678901','user123','2024-06-29','User','Not Renting'),
('U9','Olivia','Lopez','O01-22-654321','olivial@example.com','09129873456','user123','2024-12-18','User','Not Renting'),
('U10','Lucas','Torres','L07-23-234567','lucast@example.com','09456712345','user123','2025-01-30','User','Not Renting'),
('U11','Ava','Ramos','A06-21-876543','avar@example.com','09234126789','user123','2024-08-14','User','Not Renting'),
('U12','Ethan','Flores','E03-20-345678','ethanf@example.com','09127654321','user123','2024-07-03','User','Not Renting'),
('U13','Mia','Gonzales','M02-19-567890','miag@example.com','09348765210','user123','2024-10-26','User','Not Renting'),
('U14','Logan','Delacruz','L11-24-098765','logand@example.com','09432108976','user123','2024-05-24','User','Not Renting'),
('U15','Isabella','Fernandez','I08-22-135792','isabellaf@example.com','09126789432','user123','2024-07-27','User','Not Renting'),
('U16','Jacob','Martinez','J10-23-567123','jacobm@example.com','09234561230','user123','2025-04-02','User','Not Renting'),
('U17','Sophia','Domingo','S05-25-781234','sophiad@example.com','09321847654','user123','2025-01-04','User','Not Renting'),
('U18','Michael','Navarro','M06-21-675849','michaeln@example.com','09147826531','user123','2024-06-22','User','Not Renting'),
('U19','Charlotte','Silva','C12-20-432198','charlottes@example.com','09421367584','user123','2024-12-12','User','Not Renting'),
('U20','Benjamin','Aguilar','B03-24-908172','benjamina@example.com','09234127865','user123','2024-08-30','User','Not Renting');



--Vehicle Sample Data
INSERT INTO VEHICLES (VehicleID, VType, Brand, Model, Color, LicensePlate, RentPrice, VehicleStatus) VALUES
('V001', 'SUV', 'Toyota', 'Fortuner', 'Silver', 'HTY1978', 4653.66, 'Available'),
('V002', 'SUV', 'Mitsubishi', 'Montero Sport', 'Gray', 'VWS2370', 3555.46, 'Available'),
('V003', 'SUV', 'Ford', 'Everest', 'Silver', 'VEM1584', 3613.29, 'Available'),
('V004', 'SUV', 'Nissan', 'Terra', 'Blue', 'KAT2441', 3704.00, 'Available'),
('V005', 'SUV', 'Chevrolet', 'Trailblazer', 'Blue', 'AWL2528', 4273.37, 'Available'),
('V006', 'Sedan', 'Toyota', 'Vios', 'Red', 'UDD8314', 1326.78, 'Available'),
('V007', 'Sedan', 'Honda', 'City', 'Gray', 'GFA1807', 1573.11, 'Available'),
('V008', 'Sedan', 'Nissan', 'Almera', 'Gray', 'BSC3317', 1795.38, 'Available'),
('V009', 'Sedan', 'Hyundai', 'Accent', 'Red', 'JUE6727', 1270.35, 'Available'),
('V010', 'Sedan', 'MG', '5', 'White', 'OBI8919', 1890.22, 'Available'),
('V011', 'Coupe', 'Ford', 'Mustang', 'Silver', 'RJB1644', 4740.69, 'Available'),
('V012', 'Coupe', 'Chevrolet', 'Camaro', 'Red', 'FJV8274', 5773.03, 'Available'),
('V013', 'Coupe', 'Toyota', '86', 'Silver', 'MQA7966', 6428.36, 'Available'),
('V014', 'Coupe', 'Nissan', '370Z', 'Blue', 'JYZ5397', 6905.99, 'Available'),
('V015', 'Coupe', 'BMW', '2 Series', 'White', 'VQB2119', 5084.23, 'Available'),
('V016', 'Pick Up', 'Ford', 'Ranger', 'White', 'LNW9546', 3845.86, 'Available'),
('V017', 'Pick Up', 'Toyota', 'Hilux', 'White', 'OVL3915', 2783.91, 'Available'),
('V018', 'Pick Up', 'Nissan', 'Navara', 'Silver', 'RKN2922', 3356.21, 'Available'),
('V019', 'Pick Up', 'Isuzu', 'D-Max', 'White', 'GUY1859', 3779.57, 'Available'),
('V020', 'Pick Up', 'Mazda', 'BT-50', 'White', 'ZPM6843', 3417.00, 'Available'),
('V021', 'Van', 'Toyota', 'Hiace', 'Gray', 'HYZ8033', 3946.37, 'Available'),
('V022', 'Van', 'Nissan', 'NV350 Urvan', 'Gray', 'CVB4206', 3601.26, 'Available'),
('V023', 'Van', 'Hyundai', 'Starex', 'Black', 'XLT6875', 3870.08, 'Available'),
('V024', 'Van', 'Maxus', 'V80', 'Blue', 'LZF3138', 2996.30, 'Available'),
('V025', 'Van', 'Foton', 'View Traveller', 'Black', 'KHG7482', 3084.94, 'Available'),
('V026', 'Minivan', 'Kia', 'Carnival', 'Black', 'HPD7364', 2591.47, 'Available'),
('V027', 'Minivan', 'Toyota', 'Innova', 'Black', 'RVL1394', 3248.88, 'Available'),
('V028', 'Minivan', 'Mitsubishi', 'Xpander', 'Gray', 'GSJ8196', 3012.58, 'Available'),
('V029', 'Minivan', 'Suzuki', 'Ertiga', 'Red', 'NRW5577', 2234.65, 'Available'),
('V030', 'Minivan', 'Honda', 'Mobilio', 'Black', 'XYA2765', 3103.36, 'Available'),
('V031', 'Convertible', 'Mazda', 'MX-5', 'Silver', 'WPD3654', 7011.62, 'Available'),
('V032', 'Convertible', 'BMW', 'Z4', 'Red', 'NTA8473', 5316.00, 'Available'),
('V033', 'Convertible', 'Mini', 'Cooper Convertible', 'Gray', 'JRS4488', 5477.45, 'Available'),
('V034', 'Convertible', 'Audi', 'A5 Cabriolet', 'Red', 'SVM7359', 7543.20, 'Available'),
('V035', 'Convertible', 'Porsche', '911 Cabriolet', 'White', 'IWC2670', 7987.81, 'Available'),
('V036', 'Hatchback', 'Toyota', 'Wigo', 'Red', 'TKU1235', 1404.38, 'Available'),
('V037', 'Hatchback', 'Suzuki', 'Swift', 'Red', 'AMZ1942', 1012.43, 'Available'),
('V038', 'Hatchback', 'Honda', 'Brio', 'White', 'BWI6087', 1367.99, 'Available'),
('V039', 'Hatchback', 'Mitsubishi', 'Mirage', 'Blue', 'GEH3290', 1243.77, 'Available'),
('V040', 'Hatchback', 'Kia', 'Picanto', 'Silver', 'XUB8179', 1222.00, 'Available'),
('V041', 'Crossover', 'Geely', 'Coolray', 'Black', 'RHV9226', 2204.36, 'Available'),
('V042', 'Crossover', 'Toyota', 'Corolla Cross', 'Blue', 'YFM4268', 3360.90, 'Available'),
('V043', 'Crossover', 'Honda', 'HR-V', 'Blue', 'JKF6281', 3121.44, 'Available'),
('V044', 'Crossover', 'MG', 'ZS', 'Black', 'ASQ1375', 2706.91, 'Available'),
('V045', 'Crossover', 'Ford', 'Territory', 'Red', 'OUK6010', 3308.88, 'Available'),
('V046', 'Electric Vehicle', 'Nissan', 'Leaf', 'Silver', 'LHA9842', 4926.56, 'Available'),
('V047', 'Electric Vehicle', 'BYD', 'Dolphin', 'Red', 'ZFK4210', 3124.82, 'Available'),
('V048', 'Electric Vehicle', 'Hyundai', 'Ioniq 5', 'Silver', 'VCB6102', 4341.12, 'Available'),
('V049', 'Electric Vehicle', 'Tesla', 'Model 3', 'Gray', 'DFT3456', 4679.49, 'Available'),
('V050', 'Electric Vehicle', 'Wuling', 'Air EV', 'White', 'KJM1283', 2781.33, 'Available'),
('V051', 'Motorcycle', 'Yamaha', 'NMAX', 'Blue', 'UKV5740', 892.22, 'Available'),
('V052', 'Motorcycle', 'Honda', 'Click 125i', 'Silver', 'PEB4087', 681.03, 'Available'),
('V053', 'Motorcycle', 'Suzuki', 'Raider R150', 'Red', 'XMW2941', 751.86, 'Available'),
('V054', 'Motorcycle', 'Kawasaki', 'Barako II', 'Black', 'BTE7140', 976.39, 'Available'),
('V055', 'Motorcycle', 'KTM', 'Duke 200', 'Red', 'LZN8623', 823.57, 'Available');

--RENTAL_DETAILS Sample Data
INSERT INTO RENTAL_DETAILS (RentalID, AccountID, VehicleID, PickupDate, ReturnDate, BillingDate, BillAmount, PaymentMethod, RentalStatus) VALUES
('R1', 'U18', 'V041', '2025-04-16', '2025-04-21', '2025-04-23', 3597.52, 'Card', 'Completed'),
('R2', 'U9', 'V036', '2025-04-23', '2025-04-24', '2025-04-27', 4739.33, 'Card', 'Completed'),
('R3', 'U20', 'V001', '2025-05-01', '2025-05-03', '2025-05-03', 2727.82, 'Cash', 'Completed'),
('R4', 'U12', 'V053', '2025-04-28', '2025-05-05', '2025-05-06', 7276.22, 'Cash', 'Completed'),
('R5', 'U18', 'V010', '2025-04-16', '2025-04-21', '2025-04-22', 2640.73, 'Card', 'Completed'),
('R6', 'U7', 'V052', '2025-04-20', '2025-04-25', '2025-04-27', 3364.78, 'Card', 'Completed'),
('R7', 'U17', 'V013', '2025-04-15', '2025-04-20', '2025-04-22', 4641.89, 'Cash', 'Completed'),
('R8', 'U13', 'V020', '2025-04-22', '2025-04-24', '2025-04-25', 6230.64, 'Cash', 'Completed'),
('R9', 'U14', 'V034', '2025-04-12', '2025-04-17', '2025-04-17', 4655.77, 'Card', 'Completed'),
('R10', 'U8', 'V038', '2025-04-27', '2025-05-01', '2025-05-02', 5394.55, 'Cash', 'Completed');

select * from ACCOUNT

