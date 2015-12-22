<?php
$db = new mysqli("371-1-d-prod.csse.rose-hulman.edu", 'test', 'ClickBake', 'ClickBake');
if($db->connect_errno > 0){
    die('Unable to connect to database [' . $db->connect_error . ']');
}

$sql = 'CALL CCInfoStar();'; 
if(!$result = $db->query($sql)){
    die('There was an error running the query2 [' . $db->error . ']');
}
$realrow;
while ($row = $result->fetch_assoc()) {
	if ($_GET["username"] == $row['emailid']) {
		$realrow = $row;
		break 1;
	}
}
$CCNO = $realrow['CCNO'];
$ret = '';
for ($i=0;$i<strlen($CCNO)-4;$i++) {
	$ret = $ret . '*';
}
$ret = $ret . substr($CCNO, -4);
echo $ret;
?>