window.onload = function () {
	console.log('test');
	document.getElementById("signup").onclick = signUp;
	document.getElementById("signin").onclick = signIn;
	Cookies.remove('clickbake_useremail');
	Cookies.remove('clickbake_userpass');
}

signUp = function() {
	console.log("signup");
	$.cookie("clickbake_useremail", document.getElementById("newemail").value);
	$.cookie("clickbake_userpass", document.getElementById("newpass").value);
}

signIn = function() {
	console.log("signin");
	$.cookie("clickbake_useremail", document.getElementById("useremail").value);
	$.cookie("clickbake_userpass", document.getElementById("userpass").value);
}