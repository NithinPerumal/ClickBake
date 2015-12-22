use ClickBake;

show tables;

Insert into Users (emailid, userPassword, nameOfUser)
VALUES ('peruman@rose-hulman.edu', 'MEMEME', 'Nithin Perumal');

Insert into Users (emailid, userPassword, nameOfUser)
VALUES ('tiefenaw@rose-hulman.edu', 'Veigar', 'Alec Tiefenthal');

Insert into Users (emailid, userPassword, nameOfUser)
VALUES ('knispeja@rose-hulman.edu', 'knispel', 'Jacob Knispel');

CALL UsersInsert('lanejn@rose-hulman.edu', 'lol', 'Jason Lane');

CALL UsersPassword('peruman@rose-hulman.edu');

Call AddressInsert('peruman@rose-hulman.edu', 'the bestest dude', 'fdsf', 'the', 'coli', '23456', 'delivergg pls');
Call AddressInsert('mcgarrdg@rose-hulman.edu', 'tf', 'f', 's', 'f', '23436', 'delivergfgg pls');

#CALL AddressUpdate('peruman@rose-hulman.edu', 'Nithin Peru', '1652 vireo', 'the city', 'cali', '43215', 'deliver pls');

Select * from Users;
Select * from CCInfo;
Select * from PurchaseHistory;
Select * from BrandPreferences;
Select * from Allergens;
Select * from Address;

drop database ClickBake;

