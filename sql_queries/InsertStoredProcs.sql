use ClickBake;

DROP procedure if exists CCInsert;
CREATE PROCEDURE CCInsert
	(
	IN CCemailid varchar(50), CCCCNO varchar(19), CCfirstName varchar(50), CClastName varchar(50), CCSecurityNo varchar(50), CCExpirationDate varchar(10), CCAddress varchar(100)
	)

REPLACE INTO  CCInfo(emailid, CCNO, firstName, lastName, SecurityNo, ExpirationDate, Address) 
VALUES (CCemailid, CCCCNO, CCfirstName, CClastName, CCSecurityNo, CCExpirationDate, CCAddress);


DROP procedure if exists PurchaseHistoryInsert;
CREATE PROCEDURE PurchaseHistoryInsert
	(
	IN PHemailid varchar(50), PHNameOfItem varchar(50), PHDateOfPurchase varchar(10), PHPrice long
	)
Insert INTO PurchaseHistory(emailid, NameOfItem, DateOfPurchase, Price)
VALUES (PHemailid, PHNameOfItem, PHDateOfPurchase, PHPrice);

DROP procedure if exists AddressInsert;
CREATE PROCEDURE AddressInsert
	(
	IN AIemailid varchar(50), AIName varchar(50), AIStreetAddress varchar(50), AICity varchar(50), AIState varchar(50), AIZip integer, AISpecialInstructions varchar(1000)
	)
Insert INTO Address(emailid, nameOfUser, StreetAddress, City, State, Zip, SpecialInstructions)
VALUES (AIemailid, AIName, AIStreetAddress, AICity, AIState, AIZip, AISpecialInstructions);

DROP procedure if exists BrandInsert;
Create procedure BrandInsert
(
	IN BIemailid varchar(50), BIBrands varchar(10000)
)
INSERT INTO BrandPreferences(emailid, Brands)
VALUES (BIemailid, BIBrands);


DROP PROCEDURE IF EXISTS AllergentsInsert;
Create procedure AllergentsInsert
(
	IN ALIemailid varchar(50), ALIAllergen varchar(100)
)
INSERT INTO Allergens(emailid, Allergen)
VALUES (ALIemailid, ALIAllergen);


DROP procedure if exists UsersInsert;
Create procedure UsersInsert
(
	IN UIemailid varchar(50), UIuserPassword varchar(50), UInameOfUser varchar(50)
)
REPLACE INTO Users(emailid, userPassword, nameOfUser)
VALUES (UIemailid, UIuserPassword, UInameOfUser);
