use estate_manager;

insert into users(UserType, UserName, Email, Address, Password) values (1, 'Toan Nguyen', 'nbtoan@apcs.vn', 'address', '123456');
insert into users(UserType, UserName, Email, Address, Password) values (1, 'Tri Quach', 'qmtri@apcs.vn', 'address', '123456');
insert into users(UserType, UserName, Email, Address, Password) values (2, 'Toan Owner', 'nbtoan@apcs.vn', 'address', '123456');
insert into users(UserType, UserName, Email, Address, Password) values (3, 'Toan Broker', 'nbtoan@apcs.vn', 'address', '123456');

/*	AddressID int primary key auto_increment,
    City varchar(50),
    District varchar(50),
    Ward varchar(50),
    Street varchar(50),
    Address varchar(50) */
    
insert into address(City, District, Ward, Street, Address)
			values('Hồ Chí Minh', '10', '15', 'Tô Hiến Thành', '266/46');
insert into address(City, District, Ward, Street, Address)
			values('Hồ Chí Minh', '5', '2', 'Nguyễn Văn Cừ', '227');

/*	EstateDetailID int primary key auto_increment,
    Area double,
    Bathroom int,
    Bedroom int,
    Cond varchar(255),
    Description varchar(1020),
    Floor int,
    Length double,
    Width double */

insert into estate_detail(Area, Bathroom, Bedroom, Cond, Description, Floor, Length, Width)
			values(125, 6, 6, '3-years-old', 'Built for hire only', 4, 25, 5);
insert into estate_detail(Area, Bathroom, Bedroom, Cond, Description, Floor, Length, Width)
			values(25, 1, 1, 'New', 'New', 2, 5, 5);

/*	EstateID int primary key auto_increment,
	AddressID int,
    EstateDetailID int,
    Name varchar(50),
    OwnerID int,
    Rate int check (1 <= rate <= 5),
    StatusID int,
    EstateTypeID int */

insert into estate (AddressID, EstateDetailID, Name, OwnerID, StatusID, EstateTypeID, PostTime)
			values (1, 1, 'Phòng cho thuê', 3, 1, 2, '2017-05-06 05:06:07');
insert into estate (AddressID, EstateDetailID, Name, OwnerID, StatusID, EstateTypeID, PostTime)
			values (2, 2, 'Cho thuê nhà riêng', 3, 1, 4,'2017-05-05 06:07:08');

