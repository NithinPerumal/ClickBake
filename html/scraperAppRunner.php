<?php
	echo exec('java -cp ScraperApp.jar org.gradle.ScraperApp ' . $_GET['username'] . ' ' . $_GET['url'], $debug);
	print_r($debug);
	echo $_GET['username'];
	echo $_GET['url'];
?>