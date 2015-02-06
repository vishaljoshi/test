
//var productModule = function(doc,baseUrl){
/**
 * Globale Error constants
*/
var error = {};
error.connecting = "unable to connect";
error.invalid = "invalid response";
error.updated = "sucessfully updated";

/**
 * Handling the spinner and error message
 * data loads quickly hence spinner is not vissble :)
 */
var isloading = function(id, flag, errorMsg) {
	if (flag) {
		document.getElementById(id + ".spinner").style.display = 'block';
		document.getElementById(id + ".error").innerHTML = '';
	} else {
		document.getElementById(id + ".spinner").style.display = 'none';
		document.getElementById(id + ".error").innerHTML = errorMsg;
	}
}

/**
 * Handles the updating of price and title
 */
var update = function (e) {

	e = window.event || e;
	var targetElement = e.target;
	// only if hit entere key
	if (e.keyCode === 13) {
		var elementId = targetElement.id;
		var elementValue = targetElement.value;
		var errorMsg = null;
		if ((errorMsg = validate(targetElement)) === null) {
			console.log("value=" + elementValue);
			console.log("id=" + elementId);
			var req = baseUrl + "/updateProduct"
			var param = {};
			param.id = document.getElementById("productId").value;
			param.field = elementId;
			param.value = elementValue;

			isloading(elementId, true, '');

			httpService(req, 'POST', param, null, function(status, response) {
				if (response && status === 200) {
					var res = JSON.parse(response);
					if (!res.errorMsg) {

						isloading(elementId, false, error.updated);
					} else {
						isloading(elementId, false, res.errorMsg);
					}

				} else {
					isloading(elementId, false, error.invalid);
				}
			}, function(status) {
				// error handling
				isloading(elementId, false, error.connecting);
			});

		} else {
			isloading(elementId, false, errorMsg);
		}

	}
}

/**
 * loading the product after selecting the product id
 */
var loadProduct =function (searchObj, id) {
	// need to clean up the form data before loading new data.
	document.getElementById("productId").value = null;
	document.getElementById("title").value = null;
	document.getElementById("pricing.price").value = null;
	document.getElementById("cost").value = null;
	isloading(document.getElementById("title").id, false, '');
	isloading(document.getElementById("pricing.price").id, false, '');
	var req = baseUrl + "/product/" + id;

	isloading(searchObj.id, true, '');
	httpService(
			req,
			'GET',
			null,
			null,
			function(status, response) {
				if (response != null && status === 200) {
					var res = JSON.parse(response);
					if (res != null && !res.errorMsg) {
						isloading(searchObj.id, false, '');
						document.getElementById("productId").value = res.id;
						document.getElementById("title").value = res.title;
						document.getElementById("pricing.price").value = res.pricing.price;
						document.getElementById("cost").value = res.pricing.cost;
					} else {
						isloading(searchObj.id, false, res.errorMsg);
					}

				} else {
					isloading(searchObj.id, false, error.invalid);
				}
			}, function(status) {
				// error handling
				isloading(searchObj.id, false, error.connecting);
			});

}

/**
 * validation before updating the product
 * 
 */
var validate =function (element) {
	_ret = null;
	if (element.id === 'title') {
		var titlePatt = new RegExp('(?=.*[a-zA-Z])[a-zA-Z0-9]+$');
		if (!titlePatt.test(element.value)) {
			console.log("title bad");
			_ret = "incorrect title"
		}

	} else if (element.id === 'pricing.price') {

		var pricePatt = new RegExp('^((0|[1-9][0-9]*)(\.[0-9]+)?|\.[0-9]+)$');
		if (!pricePatt.test(element.value)) {
			console.log("PRICE bad");
			_ret = "incorrect price"
		} else if (element.value > document.getElementById("cost").value) {

			_ret = "price cannot be greater than cost";

		}

	}
	return _ret

}
/**
 * Handles all the auto complete functionality
 * 
 */
var autoSuggest =function (searchBoxObj, suggestBoxObj, urlForSuggest) {
	this.cur = -1;
	// alert(searchBoxObj.name);
	this.urlForSuggest = urlForSuggest;
	this.searchBoxObj = searchBoxObj;
	this.suggestBoxObj = suggestBoxObj;
	searchBoxObj.onkeyup = this.keyUp;
	searchBoxObj.onkeydown = this.keyDown;
	searchBoxObj.autoSuggest = this;
	searchBoxObj.onblur = this.hideSuggest;
}
autoSuggest.prototype.hideSuggest = function() {

	this.autoSuggest.suggestBoxObj.innerHTML = "";

	this.autoSuggest.suggestBoxObj.style.display = "none";

};
autoSuggest.prototype.keyDown = function(e) {
	e = window.event || e;
	KeyCode = e.keyCode;

	switch (KeyCode) {
	case 38: // up arrow
		this.autoSuggest.moveUp();
		break;
	case 40: // down arrow
		this.autoSuggest.moveDown();
		break;
	case 13: // return key
		this.autoSuggest.suggestBoxObj.innerHTML = "";
		// alert("1");
		this.autoSuggest.suggestBoxObj.style.display = "none";
		loadProduct(this.autoSuggest.searchBoxObj,
				this.autoSuggest.searchBoxObj.value);
		break;
	}
  };

autoSuggest.prototype.moveDown = function() {

	if (this.suggestBoxObj.childNodes.length > 0) {

		if (++this.cur > this.suggestBoxObj.childNodes.length - 1) {
			this.cur = 0;
		}
		for ( var i = 0; i < this.suggestBoxObj.childNodes.length; i++) {
			if (i == this.cur) {
				this.suggestBoxObj.childNodes[i].className = "over";
				this.searchBoxObj.value = this.suggestBoxObj.childNodes[i].firstChild.nodeValue;
			} else {
				this.suggestBoxObj.childNodes[i].className = "notOver";
			}
		}
	}

};

autoSuggest.prototype.moveUp = function() {
	if (this.suggestBoxObj.childNodes.length > 0) {
		if (--this.cur < 0) {
			this.cur = this.suggestBoxObj.childNodes.length - 1;
		}
		for ( var i = 0; i < this.suggestBoxObj.childNodes.length; i++) {
			if (i == this.cur) {
				this.suggestBoxObj.childNodes[i].className = "over";

				this.searchBoxObj.value = this.suggestBoxObj.childNodes[i].firstChild.nodeValue;

			} else {
				this.suggestBoxObj.childNodes[i].className = "notOver";
			}
		}
	}
};

autoSuggest.prototype.keyUp = function(e) {
	e = e || window.event;
	var iKeyCode = e.keyCode;
	
	if (iKeyCode == 8 || iKeyCode == 46) {
		// for backspace and delete	
	    this.autoSuggest.fireSuggest();
	} else if (iKeyCode < 32 || (iKeyCode >= 33 && iKeyCode <= 46)
			|| (iKeyCode >= 112 && iKeyCode <= 123)) { // ignore
	        // do nothing for special char . need to add more keys!!
	} else {
		this.autoSuggest.fireSuggest();
	}
};

/**
 * handles getting the  data for auto complete
 * 
 */
autoSuggest.prototype.fireSuggest = function() {
	while (this.suggestBoxObj.hasChildNodes()) {
		this.suggestBoxObj.removeChild(this.suggestBoxObj.firstChild);
	}
	this.suggestBoxObj.style.display = "none";	
	var req = this.urlForSuggest + "?id=" + this.searchBoxObj.value;
	var _t = this;
	httpService(req,'GET',	null,null,function(status, response) {
		if (response != null && status === 200) {
			var res = JSON.parse(response);
			if (res != null && !res.errorMsg) {
				
				autoSuggest.prototype.showSuggest.call(_t, res);
			} else {
				isloading(this.searchBoxObj.id, false, res.errorMsg);
			}

		} else {
			isloading(this.searchBoxObj.id, false, error.invalid);
		}
	}, function(status) {
		// error handling
		isloading(this.searchBoxObj.id, false, error.connecting);
	});

};

/**
 * 
 * displaying the auto complete
 */
autoSuggest.prototype.showSuggest = function(data) {
	var itemList = data;
	var oThis = this;
	this.cur = -1;
	if (itemList.length > 0) {
		// clear all
		while (this.suggestBoxObj.hasChildNodes()) {
			this.suggestBoxObj.removeChild(this.suggestBoxObj.firstChild);
		}
		try {

			this.suggestBoxObj.style.display = "block";

			for ( var i = 0; i < itemList.length; i++) {
				var divNew = document.createElement('div');

				divNew.innerHTML = itemList[i].id
				divNew.className = "notOver";
				this.suggestBoxObj.appendChild(divNew);
				divNew.onmouseover = function(e) {
					for ( var i = 0; i < oThis.suggestBoxObj.childNodes.length; i++) {
						oThis.suggestBoxObj.childNodes[i].className = "notOver";
					}
					this.className = "over";

				};
				divNew.onmouseout = function(e) {
					this.className = "notOver";

				};
				divNew.onmousedown = function(e) {
					oThis.searchBoxObj.value = this.innerHTML;
				};
			}

		} catch (err) {
			console.error(err);
		}
	}
};


/**
 *HTTP service to handle all the ajax calls 
 * 
 * 
 */
var httpService = function(url, method, param, headers, sucessCallBack,
		errorCallBack) {
	var ajax =  new XMLHttpRequest();
	var _send = null;
	var setHeaders = function(headers) {
		if (headers) {
			var keys = Object.keys(headers);

			for ( var i = 0; i < keys.length; i++) {
				ajax.setRequestHeader(keys[i], headers[keys[i]]);
				console.log('setting headers ' + keys[i] + '=='
						+ headers[keys[i]])
			}
		}

	};

	var paramToString = function(param) {
		_ret = '';
		if (param) {
			var keys = Object.keys(param);

			for ( var i = 0; i < keys.length; i++) {
				if (i != 0) {
					_ret += '&'
				}
				_ret += keys[i] + '=' + param[keys[i]]
			}
		}
		return _ret;
	}

	if ("GET" === method) {
		ajax.open('GET', url + (param==null? '':'?'+paramToString(param)), false);
	} else {
		ajax.open('POST', url, false);
		_send = paramToString(param);
		ajax.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
	}

	setHeaders(headers);

	ajax.onreadystatechange = function() {
		if (ajax.readyState == 4) {
			sucessCallBack(ajax.status, ajax.responseText);
		}
	};
	ajax.onerror = function() {
		errorCallBack(ajax.status);
	}
	ajax.ontimeout = function() {
		errorCallBack(ajax.status);
	}
	ajax.send(_send);

}
