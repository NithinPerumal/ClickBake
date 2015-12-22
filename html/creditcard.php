<?php
if (!isset($_COOKIE["clickbake_useremail"]) || !isset($_COOKIE["clickbake_userpass"])) {
	header('Location: index.html');
	die();
}

$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}
// Add new alergen if posted
if (isset($_POST['ccno']) && isset($_POST['firstName']) && isset($_POST['lastName']) && isset($_POST['security']) && isset($_POST['expdate']) && isset($_POST['address'])) {
	$sql = 'CALL CCInsert("' . $_COOKIE["clickbake_useremail"] . '", "' . $_POST['ccno'] . '", "' . $_POST['firstName'] . '", "' . $_POST['lastName'] . '", "' . $_POST['security'] . '", "' . $_POST['expdate'] . '", "' . $_POST['address'] .'");';
	if(!$result = $db->query($sql)){
    	die('There was an error running the query1 [' . $db->error . ']');
	}
	echo '<script language="javascript">';
	echo 'alert("Credit Card Information Successfully Submitted")';
	echo '</script>';
}

// Grab the existing alergens
$sql = 'CALL CCInfoStar();'; 
if(!$result = $db->query($sql)){
    die('There was an error running the query2 [' . $db->error . ']');
}
$realrow;
while ($row = $result->fetch_assoc()) {
	if ($_COOKIE["clickbake_useremail"] == $row['emailid']) {
		$realrow = $row;
		break 1;
	}
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
		<script src="creditcard.js" type="text/javascript"></script>
	</head>
	<body>
	<div class="main">
		<fieldset>
		<form method="post">
			<legend>Credit Card Page</legend>
			<p>Credit Card Number: <input type="text" name="ccno" value=<?php echo '"' . $realrow['CCNO'] . '"'; ?>></p>
			<p>First Name on Card: <input type="text" name="firstName" value=<?php echo '"' . $realrow['firstName'] . '"'; ?>></p>
			<p>Last Name on Card: <input type="text" name="lastName" value=<?php echo '"' . $realrow['lastName'] . '"'; ?>></p>
			<p>Security Number: <input type="text" name="security" value=<?php echo '"' . $realrow['SecurityNo'] . '"'; ?>></p>
			<p>Expiration Date: <input type="text" name="expdate" value=<?php echo '"' . $realrow['ExpirationDate'] . '"'; ?>></p>
			<p>Address: <input type="text" name="address" value=<?php echo '"' . $realrow['Address'] . '"'; ?>></p>
			<button type="submit">Submit</button></form>
		</fieldset>
		<a href="preferences.php">Back to Preferences</a>
	</div>
	</body>
</html>