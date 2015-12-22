<?php
$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}
// Add new alergen if posted
if (isset($_POST['newbrand'])) {
	$sql = 'CALL BrandInsert("' . $_COOKIE["clickbake_useremail"] . '", "' . $_POST['newbrand'] . '");';
	if(!$result = $db->query($sql)){
    	die('There was an error running the query1 [' . $db->error . ']');
	}
}

// Grab the existing alergens
$sql = 'CALL BrandPreferencesStar();'; 
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
		<h1>Brand Preferences</h1>
		<p>In order of preference</p>
		<ol>
			<?php 
			while ($row = $result->fetch_assoc()) {
				if ($_COOKIE["clickbake_useremail"] == $row['emailid']) {
				echo '<li>';
				echo $row['Brands'];
				echo '</li>';
				}
			}
			?>
		</ol>
		<h3>Add new prefered brands</h3>
		<form method="post">
		<input type="text" name="newbrand">
		<button type="submit">Add</button></form>
		<a href="preferences.php">Back to Preferences</a>
	</div>
	</body>
</html>