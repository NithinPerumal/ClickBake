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
if (isset($_POST['name']) && isset($_POST['address']) && isset($_POST['city']) && isset($_POST['state']) && isset($_POST['zip'])) {
	$sql = 'CALL AddressInsert("' . $_COOKIE["clickbake_useremail"] . '", "' . $_POST['name'] . '", "' . $_POST['address'] . '", "' . $_POST['city'] . '", "' . $_POST['state'] . '", "' . $_POST['zip'] . '", "' . $_POST['instructions'] .'");';
	if(!$result = $db->query($sql)){
    	die('There was an error running the query1 [' . $db->error . ']');
	}
	echo '<script language="javascript">';
	echo 'alert("Address Submitted")';
	echo '</script>';
}

// Grab the existing alergens
$sql = 'CALL AddressStar();'; 
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
	</head>
	<body>
	<div class="main">
		<fieldset>
		<form method="post">
			<legend>Shipping Address</legend>
			<p>Name: <input type="text" name="name" value=<?php
			echo '"';
			echo $row["nameOfUser"];
			echo '"';
			?>></p>
			<p>Address: <input type="text" name="address" value=<?php echo '"' . $realrow['StreetAddress'] . '"'; ?>></p>
			<p>City: <input type="text" name="city" value=<?php echo '"' . $realrow['City'] . '"'; ?>></p>
			<p>State: <input type="text" name="state" value=<?php echo '"' . $realrow['State'] . '"'; ?>></p>
			<p>Zip: <input type="text" name="zip" value=<?php echo '"' . $realrow['Zip'] . '"'; ?>></p>
			<p>Special Delivery Instructions: <input type="text" name="instructions" value=<?php echo '"' . $realrow['SpecialInstructions'] . '"'; ?>></p>
			<button type="submit">Submit</button></form>
		</fieldset>
		<a href="preferences.php">Back to Preferences</a>
	</div>
	</body>
</html>