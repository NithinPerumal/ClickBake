var tabURL = 'about::blank';
var SITE = 'http://371-1-d-prod.csse.rose-hulman.edu/html/';
//SITE = 'test-';
var readyToBuy = false;
var gunk = '';
var price = 0;
var username;
var userpass;

window.onload = function () {
	$("#orderset").hide();
	$('#orderdone').hide();
	$('#displayed-ingredients').hide();

	$('#cancelbutton').click(function () {
		open(location, '_self').close();
	});

	$('#donebutton').click(function () {
		open(location, '_self').close();
	});

	$('#logout').click(function () {
		Cookies.remove('username');
		Cookies.remove('userpass');
		open(location, '_self').close();
	});

	$('#changeshipping').click(function () {
		var newURL = 'http://371-1-d-prod.csse.rose-hulman.edu/html/address.php';
		chrome.tabs.create({ url : newURL})
	});

	$('#signup').click(function () {
		var newURL = 'http://371-1-d-prod.csse.rose-hulman.edu/html/index.html';
		chrome.tabs.create({ url : newURL})
	});

	$('#changecreditcard').click(function () {
		var newURL = 'http://371-1-d-prod.csse.rose-hulman.edu/html/creditcard.php';
		chrome.tabs.create({ url : newURL})
	});

	$('#orderbutton').click(orderdone);
	if ((Cookies.get('username') == null) || (Cookies.get('userpass') == null) || true) {
		$("#loading").hide();
		$('#signin').click(logintime);
	} else {
		$("#login").hide();
		scrape();
	}
};

logintime = function () {
	//chrome.cookies.set({ url: "127.0.0.1", name: "username", value: $('#useremail').value, expirationDate: (new Date().getTime()/1000) + 36000000 });
	//chrome.cookies.set({ url: "127.0.0.1", name: "userpass", value: $('#userpass').value, expirationDate: (new Date().getTime()/1000) + 36000000 });
	username = $('#useremail').val();
	userpass = $('#userpass').val();
	console.log($('#useremail').value);
	console.log(username);
	//document.cookie='username=' + $('#useremail').value;
	//console.log(chrome.cookies.get({ url: "127.0.0.1", name: "username" }));
	/*if ((Cookies.get('username') == null) || (Cookies.get('userpass') == null)) {
		return;
	}*/
	$("#login").hide();
	$("#loading").show();
	scrape();
}

function scrape() {
	getCurrentURL();
	
	console.log(tabURL);

	$.ajax(SITE + "scraperAppRunner.php",
	{
		"type": "GET",
		"data": {"username": username, "url": tabURL},
		"datatype": "text"
	}).done(websiteparsed).fail(ajaxFailure);
	console.log("I is scraping");
}

getCurrentURL = function () {
	chrome.tabs.query({
		active: true,
		currentWindow: true
		}, function(tabs) {
			var tu = tabs[0].url;
			//alert(tu);
			tabURL = tu;
			console.log(tu);
	});
};

function websiteparsed(data) {
	console.log(SITE + "tescoSearch.php");
	console.log(data);
	$.ajax(SITE + "tescoSearch.php",
	{
		"type": "GET",
		"data": {"username": username},
		"datatype": "text"
	}).done(purchaseready).fail(ajaxFailure);
};

function purchaseready(data) {
	readyToBuy = true;
	$('#loading').hide();
	$('#orderset').show();
	$('#displayed-ingredients').show();

	console.log(data);
	console.log(data.ingredients);

	data = JSON.parse(data);

	$.each(data.ingredients, function(key, value) {
		var li = $("<li>");
		li.html(value.Name + ", " + value.Price);
		$("#displayed-ingredients").append(li);
		price = price + value.Price;
		gunk = gunk + value.Name;
	});

	$.ajax(SITE + "ccget.php",
	{
		"type": "GET",
		"data": {"username": username, "password": userpass},
		"datatype": "text"
	}).done(ccload).fail(ajaxFailure);

	$.ajax(SITE + "addressget.php",
	{
		"type": "GET",
		"data": {"username": username, "password": userpass},
		"datatype": "text"
	}).done(addressload).fail(ajaxFailure);
};

function orderdone() {
	$('#orderdone').show();
	$('#orderset').hide();
	$('#orderbutton').hide();
	$('#displayed-ingredients').hide();
	$.ajax(SITE + "doOrder.php",
	{
		"type": "GET",
		"data": {"username": username, "password": userpass, "gunk": gunk, "price" : price},
		"datatype": "text"
	});
};

function ajaxFailure(xhr, status, exception) {
	console.log(xhr, status, exception);
};

function ccload(data) {
	console.log('test');
	console.log(data);
	console.log(username);
	$('#loadingcc').hide();
	$('#ccinfo').html(data);
}

function addressload(data) {
	$('#loadingaddress').hide();
	$('#shippingaddress').html(data);
}