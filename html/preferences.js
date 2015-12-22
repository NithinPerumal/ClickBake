window.onload = function () {
	document.getElementsByClassName('username')[0].innerHTML = $.cookie("clickbake_useremail");
	document.getElementById('addressespage').onclick = addressespage;
	document.getElementById('creditcardpage').onclick = creditcardpage;
	document.getElementById('brandpreferencespage').onclick = brandpreferencespage;	
	document.getElementById('alergenspage').onclick = alergenspage;
	document.getElementById('signout').onclick = signout;
}

addressespage = function () {
	window.location.href = 'address.php';
}

creditcardpage = function () {
	window.location.href = 'creditcard.php';
}

brandpreferencespage = function () {
	window.location.href = 'brands.php';
}

alergenspage = function () {
	window.location.href = 'alergens.php';
}

signout = function () {
	Cookies.remove('clickbake_useremail');
	Cookies.remove('clickbake_userpass');
	window.location.href = 'index.html';
}