use estate_manager;

insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (1, 'test', 'Nguyễn Bảo Toàn', 'nbtoan+1@apcs.vn', '01644701671', 'Quận 10', '1');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (2, 'test2', 'Quách Minh Trí', 'nbtoan+2@apcs.vn', '01644701671', 'Quận 2', '2');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (2, 'test3', 'Võ Thị Nhật Linh', 'nbtoan+3@apcs.vn', '01644701671', 'Quận 10', '3');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (2, 'test4', 'Mai Đào Duy Hiếu', 'nbtoan+4@apcs.vn', '01644701671', 'Quận 5', '4');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (1, 'test5', 'Võ Anh Hoàng', 'test@test.vn', '01644701671', 'Quận 8', '5');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (2, 'test6', 'Lê Sử Trường Giang', 'test@test.vn', '01644701671', 'Tân Bình', '6');
    
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', 'Tân Phú', 'Hiệp Tân', '239 - 241 và 278 Hòa Bình');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', '8', 'Căn hộ chung cư tại City Gate Towers', '');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', 'Thủ Đức', 'Linh Trung', 'Dự án Depot Metro Tower, Đường Lê Văn Chí');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', '2', '', 'Dự án Citi Soho');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', '7', 'Phú Thuận', 'Dự án Saigon Panorama, Đường Hoàng Quốc Việt');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', '12', 'Tân Thới Nhất', 'Dự án Prosper Plaza, Đường Phan Văn Hớn');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', 'Bình Tân', 'Bình Hưng Hòa A', 'Dự án 8X Rainbow, Đường Bình Long');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', '2', '', 'Dự án Lakeview City');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', 'Bình Chánh', '', 'Đường Quốc Lộ 50');
insert into address(City, District, Ward, Address)
			values('Hồ Chí Minh', '2', 'Thảo Điền', 'Dự án Masteri Thảo Điền, Đường Xa Lộ Hà Nội');

insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (1, 'Căn hộ Tân Phú Richstar Novaland, giá chỉ 1,6 tỷ/căn 2pn, lh: 0908389894', 2, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 1600, 65, 1);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (2, 'Cần bán gấp căn hộ 3pn chỉ 1,99 tỷ nhận nhà liền, hỗ trợ vay 80%, ls 6,4% cố định', 3, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 1990, 92.23, 6);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (3, 'Căn hộ trung tâm quận thủ đức, nhận nhà ở ngay, sổ hồng cuối năm. Lh pkd: 0964606646', 4, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 1250, 79, 8);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (4, 'Chính chủ bán lại căn hộ citisoho block a, b và c giá tốt. Liên hệ 0942000865', 2, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 1080, -1, 13);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (5, 'Sài gòn panorama - chỉ 17 triệu/tháng bạn đã sở hữu căn hộ liền kề phú mỹ hưng', 3, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 1700, 55.3, 16);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (6, 'Bán căn hộ cao cấp duy nhất tại tây bắc sài gòn, ngay cạnh trường chinh - cộng hòa. Giá chỉ 19tr/m2', 4, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 931, 49, 19);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (7, '20 snb căn hộ sắp nhận nhà 1/2018, giáp tân phú, chỉ 900tr/căn, góp 8tr/tháng. Lh: 0935358383', 2, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 1200, 63, 22);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (8, 'Cần bán shophouse - biệt thự xây sẵn mặt tiền song hành - an phú - quận 2. Lh: 0969949999', 3, 1, 3, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 5000, 140, 25);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (9, 'Chính chủ bán nhà 1 trệt 1 lầu giá từ 450 triệu đến 720 triệu', 4, 1, 2, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 680, 40, 27);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (10, 'Kẹt tiền bán gấp căn hộ masteri thảo điền, cam kết giá thật lh: 0933859311', 2, 1, 1, '2017-08-06 05:06:07', '2017-08-06 05:06:07', 2400, 60, 31);

insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(1, 2, 2, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(2, 2, 3, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(3, 2, 2, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(4, -1, -1, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(5, 2, 1, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(6, -1, -1, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(7, -1, -1, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(8, 4, 4, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 5);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(9, 2, 2, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 2);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(10, 2, 2, '', 'Công viên, hồ bơi, nhà trẻ, khu vui chơi trẻ em, gym, yoga, café, nhà hàng, phòng sinh\nhoạt cộng đồng giúp thỏa mãn nhu cầu sống hiện đại của bạn và gia đình.\n+ NỘI THẤT: Sàn gỗ phòng ngủ, tủ bếp trên dưới, thiết bị vệ sinh Toto, cửa lõi thép chống cháy, thẻ từ an ninh.\n+ PHÁP LÝ: hoàn chỉnh nhất khu vực, nhận sổ sau 6 tháng bàn giao nhà', 1);
            
insert into interested_estate (EstateID, BuyerID, Rate)
			values (1, 1, 3);
insert into interested_estate (EstateID, BuyerID, Rate)
			values (2, 1, 4);
insert into interested_estate (EstateID, BuyerID, Rate)
			values (1, 2, 3);
insert into interested_estate (EstateID, BuyerID, Rate)
			values (2, 2, 4);
            
insert into visited_estate (EstateID, BuyerID, Time)
			values (1, 1, '2017-05-06 05:06:07');
insert into visited_estate (EstateID, BuyerID, Time)
			values (2, 1, '2017-05-06 05:06:07');
            
insert into comment (User1, User2, EstateID, Answer, Question, Time)
			values (1, 2, 1, 'nhà mới xây được 2 tháng', 'cho hỏi tuổi của căn hộ?', '2017-08-06 05:06:07');
insert into comment (User1, User2, EstateID, Question, Time)
			values (1, 2, 1, 'cho tôi xin địa chỉ chính xác để xem nhà', '2017-08-06 05:06:07');
            
insert into search_history (UserID, District) values (1, '2')
             
