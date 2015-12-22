<?php
	exec('java -cp KitchenMaster-1.0.jar org.gradle.APIcaller ' . $_GET['username'] . ' -o', $debug);

	$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
	if($db->connect_errno > 0){
	    die('Unable to connect to database [' . $db->connect_error . ']');
	}
	// Add new alergen if posted
	if (isset($_GET['password']) && isset($_GET['gunk']) && isset($_GET['price'])) { 
		$sql = 'CALL PurchaseHistoryInsert("' . $_COOKIE["clickbake_useremail"] . '", "' . $_GET['gunk'] . '", "' . '2015' . '", "' . $_GET['price']'");';
		if(!$result = $db->query($sql)){
	    	die('There was an error running the query1 [' . $db->error . ']');
		}
	}

	$sql = 'CALL PurchaseHistoryStar();'; 
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