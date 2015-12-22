<?php
$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}
// if (isset($_COOKIE["clickbake_useremail"])) {
// 	echo 'email';
// }
// if (isset($_COOKIE["clickbake_userpass"])) {
// 	echo 'pass';
// }
if (!isset($_COOKIE["clickbake_useremail"]) || !isset($_COOKIE["clickbake_userpass"])) {
	header('Location: index.html');
	die();
}
$sql = 'CALL UsersInsert("' . $_COOKIE["clickbake_useremail"] . '","' . $_COOKIE["clickbake_userpass"] . '","' . $_COOKIE["clickbake_useremail"] . '");';
if(!$result = $db->query($sql)){
	//echo $sql;
   	//die('There was an error running the query [' . $db->error . ']');
	header('Location: LoginFailed.php');
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
	</head>
	<body>
	<div class="main">
		<h1>Welcome to ClickBake!</h1>
		<a href="preferences.php">Go to preferences</a>
	</div>
	</body>
</html>