<?php
$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}
// Add new alergen if posted
if (!isset($_COOKIE["clickbake_useremail"]) || !isset($_COOKIE["clickbake_userpass"])) {
	header('Location: index.html');
	die();
}
$sql = 'CALL UsersPassword("' . $_COOKIE["clickbake_useremail"] . '");';
if(!$result = $db->query($sql)){
   	die('There was an error running the query [' . $db->error . ']');
}
$true = 0;
while ($row = $result->fetch_assoc()) {
	if ($row['userPassword'] == $_COOKIE["clickbake_userpass"]) {
		$true++;
	}
}
if ($true == 0) {
	header('Location: index.html');
	die();
}
?>
<!DOCTYPE html>
<html>
	<head>
		<title>ClickBake</title>
		<meta charset="utf-8" />
		<link href="clickbake.css" type="text/css" rel="stylesheet" />
		<link href="favicon.ico" rel="icon" type="image/x-icon" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js" type="text/javascript"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js" type="text/javascript"></script>
		<script src="preferences.js" type="text/javascript"></script>
	</head>
	<body>
	<div class="main">
		<h1>Preferences</h1>
		<h2>Welcome back <span class="username">user</span></h2>
		<hr />
		<h3>Would you like to...</h3>
		<div class="pagebutton" id="addressespage">Change Addresses</div>
		<div class="pagebutton" id="creditcardpage">Change Credit Card Information</div>
		<div class="pagebutton" id="brandpreferencespage">Set Brand Preferences</div>
		<div class="pagebutton" id="alergenspage">Set Allergens</div>
		<div class="pagebutton" id="signout">Sign Out</div>
	</div>
	</body>
</html>