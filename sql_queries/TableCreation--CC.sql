use ClickBake;

DROP TABLE IF EXISTS Users;
CREATE TABLE Users
(
	emailid varchar(50) NOT NULL,
    userPassword varchar(50) NOT NULL,
    nameOfUser varchar(50),
    primary key (emailid)
);

DROP TABLE IF EXISTS CCInfo;
Create TABLE CCInfo
(
	emailid varchar(50) NOT NULL,
	CCNO varchar(29) NOT NULL,
    firstName varchar(50) NOT NULL,
    lastName varchar(50) NOT NULL,
    SecurityNo varchar(50) NOT NULL,
    ExpirationDate varchar(10) NOT NULL,
    Address varchar(100) NOT NULL,
    PRIMARY KEY (emailid),
    FOREIGN KEY (emailid) REFERENCES Users(emailid)
);

DROP TABLE IF EXISTS PurchaseHistory;
Create TABLE PurchaseHistory
(
	emailid varchar(50) NOT NULL,
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
    NameOfItem varchar(1000) NOT NULL,
    DateOfPurchase varchar(10) NOT NULL,
    Price long NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (emailid) REFERENCES Users(emailid)
);

DROP TABLE IF EXISTS Address;
Create TABLE Address
(
	emailid varchar(50),
    nameOfUser varchar(50),
    StreetAddress varchar(50),
    City varchar(50),
    State varchar(50),
    Zip integer,
    SpecialInstructions varchar(1000),
    primary key (emailid),
    FOREIGN KEY (emailid) REFERENCES Users(emailid)
);

DROP TABLE IF EXISTS BrandPreferences;
Create TABLE BrandPreferences 
(
	emailid varchar(50) NOT NULL,
    Brands varchar(1000),
    FOREIGN KEY (emailid) REFERENCES Users(emailid)
);

DROP TABLE IF EXISTS Allergens;
CREATE TABLE Allergens
(
	emailid varchar(50) NOT NULL,
    Allergen varchar(100),
    FOREIGN KEY (emailid) REFERENCES Users(emailid)
);