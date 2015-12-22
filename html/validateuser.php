<?php
$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

$sql = 'CALL UsersPassword(' . $_GET['username'] . ');'; 
if(!$result = $db->query($sql)){
    die('There was an error running the query2 [' . $db->error . ']');
}
$realrow=0;
while ($row = $result->fetch_assoc()) {
	if ($_GET["password"] == $row['userPassword']) {
		$realrow++;
		break 1;
	}
}
if ($realrow > 0) {
	echo 'true';
} else {
	echo 'false';
}
?>