<?php  
	$filepath = 'json/' . $_GET['username'] . '-tesco.json';
	$myfile = fopen($filepath, "r") or die("Unable to open file!");
	echo fread($myfile,filesize($filepath));
	fclose($myfile);
?>