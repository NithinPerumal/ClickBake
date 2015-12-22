use ClickBake;

DROP PROCEDURE IF EXISTS UsersSpecifics;
Create procedure UserSpecifics (IN email varchar(50)) Select * from Users WHERE emailid = email;

DROP PROCEDURE if exists UsersPassword;
Create procedure UsersPassword (IN email varchar(50)) Select userPassword from Users where emailid = email;