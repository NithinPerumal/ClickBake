use ClickBake;
    
DROP procedure if exists AddressUpdate;
CREATE PROCEDURE AddressUpdate
	(
		AIEmailID varchar(50), AInameOfUser varchar(50), AIStreetAddress varchar(50), AICity varchar(50), AIState varchar(50), AIZip integer, AISpecialInstructions varchar(1000)
	)
UPDATE Address SET nameOfUser=AInameOfUser, StreetAddress=AIStreetAddress, City=AICity, State=AIState, Zip=AIZip, SpecialInstructions=AISpecialInstructions where emailid=AIEmailID;

DROP procedure if exists CCUpdate;
CREATE PROCEDURE CCUpdate
	(
		CCEmailID varchar(50), CCCCNO varchar(19), CCfirstName varchar(50), CClastName varchar(50), CCSecurityNo varchar(50), CCExpirationDate varchar(50), CCAddress varchar(100)
	)
UPDATE Address SET  CCNO=CCCCNO, firstName=CCfirstName, lastName=CClastName, SecurityNo=CCSecurityNo, ExpirationDate=CCExpirationDate, Address=CCAddress where emailid=CCEmailID