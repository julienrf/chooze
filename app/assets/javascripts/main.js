(function () { "use strict";
var AntiFlood = function() { }
AntiFlood.main = function() {
	var fs = js.Browser.document.querySelectorAll("form");
	var _g1 = 0, _g = fs.length;
	while(_g1 < _g) {
		var i = _g1++;
		var f = [fs.item(i)];
		var disableButtons = (function(f) {
			return function(e) {
				var $is = f[0].querySelectorAll("input[type=submit], button:not([type=button])");
				var _g3 = 0, _g2 = $is.length;
				while(_g3 < _g2) {
					var j = _g3++;
					var i1 = $is.item(j);
					i1.disabled = true;
				}
			};
		})(f);
		f[0].addEventListener("submit",disableButtons);
	}
}
var Chooze = function() { }
Chooze.main = function() {
	AntiFlood.main();
	Locale.main();
	Notification.main();
}
var Dom = function() { }
Dom.loop = function(e,traverse,p) {
	var node = traverse(e);
	if(node == null) return null; else if(p(node)) return node; else return Dom.loop(node,traverse,p);
}
Dom.closest = function(e,p) {
	return Dom.loop(e,function(a) {
		return a.parentNode;
	},p);
}
Dom.prev = function(e,p) {
	return Dom.loop(e,function(a) {
		return a.previousSibling;
	},p);
}
Dom.remove = function(e) {
	e.parentNode.removeChild(e);
}
var Locale = function() { }
Locale.main = function() {
	var form = js.Browser.document.querySelector("form.change-locale");
	var submit = function(e) {
		form.submit();
	};
	form.addEventListener("change",submit);
}
var Notification = function() { }
Notification.main = function() {
	var handleNotification = function(e) {
		var target = e.target;
		if(target.classList.contains("close-notification")) {
			var notification = Dom.closest(target,function(e1) {
				return e1.classList.contains("notification");
			});
			if(notification != null) Dom.remove(notification);
		}
	};
	js.Browser.document.addEventListener("click",handleNotification);
}
var js = {}
js.Browser = function() { }
js.Browser.document = typeof window != "undefined" ? window.document : null;
Chooze.main();
})();
