use estate_manager;

insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (1, 'test', 'tester', 'test@test.vn', '2234', 'address', '1');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (2, 'test2', 'tester', 'test@test.vn', '2234', 'address', '2');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (2, 'test3', 'tester', 'test@test.vn', '2234', 'address', '3');
insert into users(UserType, UserName, FullName, Email, Phone, Address, Password) 
			values (3, 'test3', 'Nguyen Bao Toan 3', 'nbtoan+3@apcs.vn', '4234', 'address', '4');
    
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
			values (1, 'CĂN HỘ TÂN PHÚ RICHSTAR NOVALAND, GIÁ CHỈ 1,6 TỶ/CĂN 2PN, LH: 0908389894', 2, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 1600, 65, 1);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (2, 'CẦN BÁN GẤP CĂN HỘ 3PN CHỈ 1,99 TỶ NHẬN NHÀ LIỀN, HỖ TRỢ VAY 80%, LS 6,4% CỐ ĐỊNH', 3, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 1990, 92.23, 6);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (3, 'CĂN HỘ TRUNG TÂM QUẬN THỦ ĐỨC, NHẬN NHÀ Ở NGAY, SỔ HỒNG CUỐI NĂM. LH PKD: 0964606646', 2, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 1250, 79, 8);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (4, 'CHÍNH CHỦ BÁN LẠI CĂN HỘ CITISOHO BLOCK A, B VÀ C GIÁ TỐT. LIÊN HỆ 0942000865', 3, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 1080, -1, 13);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (5, 'SÀI GÒN PANORAMA - CHỈ 17 TRIỆU/THÁNG BẠN ĐÃ SỞ HỮU CĂN HỘ LIỀN KỀ PHÚ MỸ HƯNG', 2, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 1700, 55.3, 16);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (6, 'BÁN CĂN HỘ CAO CẤP DUY NHẤT TẠI TÂY BẮC SÀI GÒN, NGAY CẠNH TRƯỜNG CHINH - CỘNG HÒA. GIÁ CHỈ 19TR/M2', 3, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 931, 49, 19);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (7, '20 SNB CĂN HỘ SẮP NHẬN NHÀ 1/2018, GIÁP TÂN PHÚ, CHỈ 900TR/CĂN, GÓP 8TR/THÁNG. LH: 0935358383', 2, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 1200, 63, 22);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (8, 'CẦN BÁN SHOPHOUSE - BIỆT THỰ XÂY SẴN MẶT TIỀN SONG HÀNH - AN PHÚ - QUẬN 2. LH: 0969949999', 3, 1, 3, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 5000, 140, 25);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (9, 'CHÍNH CHỦ BÁN NHÀ 1 TRỆT 1 LẦU GIÁ TỪ 450 TRIỆU ĐẾN 720 TRIỆU', 2, 1, 2, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 680, 40, 27);
insert into estate (AddressID, Name, OwnerID, StatusID, EstateTypeID, PostTime, EditTime, Price, Area, PhotoID)
			values (10, 'KẸT TIỀN BÁN GẤP CĂN HỘ MASTERI THẢO ĐIỀN, CAM KẾT GIÁ THẬT LH: 0933859311', 3, 1, 1, '2017-05-06 05:06:07', '2017-05-06 05:06:07', 2400, 60, 31);

insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(1, 2, 2, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(2, 2, 3, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(3, 2, 2, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(4, -1, -1, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(5, 2, 1, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(6, -1, -1, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(7, -1, -1, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(8, 4, 4, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 5);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(9, 2, 2, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 2);
insert into estate_detail(EstateID, Bathroom, Bedroom, Cond, Description, Floor)
			values(10, 2, 2, '', 'Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description Description\n Description Description Description Description Description Description\n\n Description Description Description Description Description Description\n Description Description Description Description\n Description Description Description Description Description\n Description Description\n Description Description Description Description Description Description Description\n\n Description Description Description Description', 1);

            
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
