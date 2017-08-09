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
    FullName varchar(50),
    Email varchar(50),
    Address varchar(50),
    Phone varchar(15),
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
insert into user_type values (2, 'Owner/Broker');
insert into user_type values (3, 'Buyer/Owner/Broker');

/* ESTATE */

create table estate (
	EstateID int primary key auto_increment,
	AddressID int,
    EstateTypeID int,
    Name varchar(255),
    OwnerID int,
    Rate int,
    StatusID int,
    PostTime datetime,
    EditTime datetime,
    Price double,
    Area double,
    PhotoID int
);

create table interested_estate (
	EstateID int references estate (EstateID),
    BuyerID int references users (UserID),
    Rate int
);

create table visited_estate (
	EstateID int references estate (EstateID),
    BuyerID int references users (UserID),
    isVisited boolean,
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

create table photo (
	PhotoID int primary key auto_increment,
    Photo longtext,
    Description varchar(255),
    EstateID int references estate (EstateID),
    Time datetime
);

create table estate_detail (
	EstateID int primary key,
    Bathroom int,
    Bedroom int,
    Cond varchar(255),
    Description varchar(1020),
    Floor int,
    Length double,
    Width double,
    Longitude double,
    Latitude double
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
    Address varchar(50)
);

create table note (
	NoteID int auto_increment primary key,
    Note varchar(1020),
    EstateID int references estate(EstateID),
    UserID int references users(UserID)
);

create table photo_note (
	NoteID int auto_increment primary key,
    EstateID int references estate(EstateID),
    Photo longtext,
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
    Note varchar(1025),
    Title varchar(255),
    Time datetime,
    Status int references appt_status(StatusID),
    User1 int references users(UserID),
    User2 int references users(UserID),
    EstateID int
);

/* MESSAGE */

create table message (
	MessageID int primary key auto_increment,
    Message varchar(1020),
    Sender int references users(UserID),
    Receiver int references users(UserID),
    Time datetime
);

/* AUTHENTICATION */

create table token_login (
	UserID int references users(UserID),
    Token varchar(20)
);

create table token_resetPassword (
	UserID int references users(UserID),
    token varchar(5)
);

/* NOTIFICATION */

create table noti_type (
	TypeID int,
    TypeName varchar(20)
);

create table notification (
	ID int primary key auto_increment,
	UserID int references users(UserID),
    RequestID int references users(UserID),
    EstateID int references estate(EstateID),
    Message text,
    NotiType int
);

create table report_user (
	UserID int references users(UserID),
    ReportMessage text
);

/* PERSONAL COMMENT */

create table comment (
	ID int primary key auto_increment,
    User1 int,
    User2 int,
    EstateID int,
    Answer varchar(255),
    Question varchar(255),
    Time datetime
);

