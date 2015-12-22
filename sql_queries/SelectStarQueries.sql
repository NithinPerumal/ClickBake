use ClickBake;

DROP Procedure if exists CCInfoStar;
CREATE Procedure CCInfoStar() Select * From CCInfo;

DROP Procedure if exists PurchaseHistoryStar;
CREATE Procedure PurchaseHistoryStar() Select * From PurchaseHistory;

DROP Procedure if exists AddressStar;
CREATE Procedure AddressStar() Select * From Address;

DROP Procedure if exists BrandPreferencesStar;
CREATE Procedure BrandPreferencesStar() Select * From BrandPreferences;

DROP Procedure if exists AllergensStar;
CREATE Procedure AllergensStar() Select * From Allergens;

DROP Procedure if exists UsersStar;
CREATE Procedure UsersStar() Select * From Users;