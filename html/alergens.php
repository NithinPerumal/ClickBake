<?php
$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}
// Add new alergen if posted
if (isset($_POST['newallergen'])) {
	$sql = 'CALL AllergentsInsert("' . $_COOKIE["clickbake_useremail"] . '", "' . $_POST['newallergen'] . '");';
	if(!$result = $db->query($sql)){
    	die('There was an error running the query1 [' . $db->error . ']');
	}
}

// Grab the existing alergens
$sql = 'CALL AllergensStar();'; 
if(!$result = $db->query($sql)){
    die('There was an error running the query2 [' . $db->error . ']');
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
		<h1>Allergens</h1>
		<h2>Please note that this feature is not fully supported yet, so use discretion if you are allergic</h2>
		<p>Current Allergies</p>
		<ul>
			<?php echo $_COOKIE["clickbake_useremail"];
			while ($row = $result->fetch_assoc()) {
				if ($_COOKIE["clickbake_useremail"] == $row['emailid']) {
				echo '<li>';
				echo $row['Allergen'];
				echo '</li>';
			}
			}
			?>
		</ul>
		<h3>Add additional allergies</h3>
		<form method="post">
		<input type="text" name="newallergen">
		<button type="submit">Add</button></form>
		<a href="preferences.php">Back to Preferences</a>
	</div>
	</body>
</html>