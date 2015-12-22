<?php
	exec('java -cp KitchenMaster-1.0.jar org.gradle.APIcaller ' . $_GET['username'], $debug);
	//print_r($debug);
	exec('chmod 777 json/*');
	$filepath = 'json/' . $_GET['username'] . '-tesco.json';
	$myfile = fopen($filepath, "r") or die("Unable to open file!" . $_GET['username']);
	echo fread($myfile,filesize($filepath));
	fclose($myfile);
?>