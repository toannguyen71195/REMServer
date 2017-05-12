Drop database estate_manager;

CREATE DATABASE estate_manager
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

USE estate_manager;

/* USER */

create table users (
	UserID int auto_increment,
    UserType int,
    UserName varchar(50),
    Email varchar(50),
    Address varchar(50),
    Password varchar(50),
    primary key (UserID)
);

create table user_type (
	TypeID int,
    TypeName varchar(20),
    primary key (TypeID)
);

alter table users
add foreign key (UserType) references user_type (TypeID);

insert into user_type values (1, 'Buyer');
insert into user_type values (2, 'Owner');
insert into user_type values (3, 'Broker');

/* ESTATE */

create table estate (
	EstateID int primary key auto_increment,
	AddressID int,
    EstateDetailID int,
    Name varchar(50),
    OwnerID int,
    Rate int check (1 <= rate <= 5),
    StatusID int,
    EstateTypeID int,
    PostTime datetime,
    Price double
);

create table interested_estate (
	EstateID int references estate (EstateID),
    BuyerID int references users (UserID),
    isVisited bool,
    Time datetime
);

create table estate_type (
	EstateTypeID int primary key,
	TypeName varchar(50)
);

insert into estate_type values (1, 'Căn hộ chung cư');
insert into estate_type values (2, 'Nhà riêng');
insert into estate_type values (3, 'Biệt thự');
insert into estate_type values (4, 'Nhà mặt phố');
insert into estate_type values (5, 'Đất');
insert into estate_type values (6, 'Đất nền dự án');
insert into estate_type values (7, 'Trang trại');
insert into estate_type values (8, 'Khu nghỉ dưỡng');
insert into estate_type values (9, 'Kho, xưởng');

create table estate_status (
	StatusID int primary key,
    Name varchar(20)
);

insert into estate_status values (1, 'Available');
insert into estate_status values (2, 'Sold');

create table comment (
	CommentID int primary key auto_increment,
    Comment varchar(1020),
    EstateID int references estate (EstateID),
    Time datetime,
    UserID int references users (UserID)
);

create table photo (
	PhotoID int primary key auto_increment,
    Photo varchar(510),
    EstateID int references estate (EstateID),
    Time datetime
);

create table estate_detail (
	EstateDetailID int primary key auto_increment,
    Area double,
    Bathroom int,
    Bedroom int,
    Cond varchar(255),
    Description varchar(1020),
    Floor int,
    Length double,
    Width double
);

create table broker_estate (
	BrokerID int references users(UserID),
    EstateID int references estate(EstateID)
);

create table address (
	AddressID int primary key auto_increment,
    City varchar(50),
    District varchar(50),
    Ward varchar(50),
    Street varchar(50),
    Address varchar(50)
);

create table note (
	NoteID int auto_increment primary key,
    Note varchar(1020),
    EstateID int references estate(EstateID),
    PhotoID int references photo(PhotoID),
    Time datetime,
    UserID int references users(UserID)
);

/*
alter table estate
add foreign key (EstateTypeID) references estate_type (EstateTypeID),
add foreign key (StatusID) references estate_status (StatusID),
add foreign key (AddressID) references address (AddressID),
add foreign key (EstateDetailID) references estate_detail (EstateDetailID);
*/

/* APPOINTMENT */

create table appt_status (
	StatusID int primary key,
    Name varchar(20)
);

insert into appt_status values (1, 'Pending');
insert into appt_status values (2, 'Accepted');
insert into appt_status values (3, 'Rejected');
insert into appt_status values (4, 'Completed');

create table appointment (
	ApptID int primary key auto_increment,
    Address varchar(255),
    Note varchar(255),
    Time datetime,
    Status int references appt_status(StatusID),
    User1 int references users(UserID),
    User2 int references users(UserID)
);

/* MESSAGE */

create table message (
	MessageID int primary key auto_increment,
    Message varchar(1020),
    Sender int references users(UserID),
    Receiver int references users(UserID),
    Time datetime
);