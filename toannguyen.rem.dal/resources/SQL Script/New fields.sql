use estate_manager;

update estate_detail set Latitude = 10.7727133, Longitude = 106.6276567 where EstateID = 1;
update estate_detail set Latitude = 10.7309519, Longitude = 106.6264569 where EstateID = 2;
update estate_detail set Latitude = 10.87461, Longitude = 106.808352 where EstateID = 3;
update estate_detail set Latitude = 10.77458, Longitude = 106.705912 where EstateID = 4;
update estate_detail set Latitude = 10.7141855, Longitude = 106.737809 where EstateID = 5;
update estate_detail set Latitude = 10.8283239, Longitude = 106.6197165 where EstateID = 6;
update estate_detail set Latitude = 10.779079, Longitude = 106.61995 where EstateID = 7;
update estate_detail set Latitude = 10.778149, Longitude = 106.699612 where EstateID = 8;
update estate_detail set Latitude = 10.6546507, Longitude = 106.6548443 where EstateID = 9;
update estate_detail set Latitude = 10.8022816, Longitude = 106.7409646 where EstateID = 10;
update estate_detail set Latitude = 10.7727133, Longitude = 106.6276567 where EstateID = 11;
update estate_detail set Latitude = 10.7309519, Longitude = 106.6264569 where EstateID = 12;
update estate_detail set Latitude = 10.87461, Longitude = 106.808352 where EstateID = 13;
update estate_detail set Latitude = 10.77458, Longitude = 106.705912 where EstateID = 14;
update estate_detail set Latitude = 10.7141855, Longitude = 106.737809 where EstateID = 15;
update estate_detail set Latitude = 10.8283239, Longitude = 106.6197165 where EstateID = 16;
update estate_detail set Latitude = 10.779079, Longitude = 106.61995 where EstateID = 17;
update estate_detail set Latitude = 10.778149, Longitude = 106.699612 where EstateID = 18;
update estate_detail set Latitude = 10.6546507, Longitude = 106.6548443 where EstateID = 19;
update estate_detail set Latitude = 10.8022816, Longitude = 106.7409646 where EstateID = 20;

ALTER TABLE estate
    ADD FULLTEXT INDEX estate (Name);
ALTER TABLE estate_detail
    ADD FULLTEXT INDEX estate_detail (Description);
ALTER TABLE estate_type
    ADD FULLTEXT INDEX estate_type (TypeName);