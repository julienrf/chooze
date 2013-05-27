(function () { "use strict";
var Dom = function() { }
Dom.__name__ = true;
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
var HxOverrides = function() { }
HxOverrides.__name__ = true;
HxOverrides.cca = function(s,index) {
	var x = s.charCodeAt(index);
	if(x != x) return undefined;
	return x;
}
var Std = function() { }
Std.__name__ = true;
Std.string = function(s) {
	return js.Boot.__string_rec(s,"");
}
Std.parseInt = function(x) {
	var v = parseInt(x,10);
	if(v == 0 && (HxOverrides.cca(x,1) == 120 || HxOverrides.cca(x,1) == 88)) v = parseInt(x);
	if(isNaN(v)) return null;
	return v;
}
var Vote = function() { }
Vote.__name__ = true;
Vote.main = function() {
	var form = js.Browser.document.querySelector("form");
	if(form != null) {
		var update = function(e) {
			var target = e.target;
			var inputs = form.querySelectorAll(".alternative > input[type=range]");
			var _g1 = 0, _g = inputs.length;
			while(_g1 < _g) {
				var i = _g1++;
				if(inputs.item(i) == e.target) {
					var n = Dom.prev(target,function(a) {
						return a.className == "name";
					});
					if(n != null) n.style.fontWeight = Std.string(100 + Std.parseInt(target.value) * 8);
				}
			}
		};
		form.addEventListener("change",update);
	}
}
var js = {}
js.Boot = function() { }
js.Boot.__name__ = true;
js.Boot.__string_rec = function(o,s) {
	if(o == null) return "null";
	if(s.length >= 5) return "<...>";
	var t = typeof(o);
	if(t == "function" && (o.__name__ || o.__ename__)) t = "object";
	switch(t) {
	case "object":
		if(o instanceof Array) {
			if(o.__enum__) {
				if(o.length == 2) return o[0];
				var str = o[0] + "(";
				s += "\t";
				var _g1 = 2, _g = o.length;
				while(_g1 < _g) {
					var i = _g1++;
					if(i != 2) str += "," + js.Boot.__string_rec(o[i],s); else str += js.Boot.__string_rec(o[i],s);
				}
				return str + ")";
			}
			var l = o.length;
			var i;
			var str = "[";
			s += "\t";
			var _g = 0;
			while(_g < l) {
				var i1 = _g++;
				str += (i1 > 0?",":"") + js.Boot.__string_rec(o[i1],s);
			}
			str += "]";
			return str;
		}
		var tostr;
		try {
			tostr = o.toString;
		} catch( e ) {
			return "???";
		}
		if(tostr != null && tostr != Object.toString) {
			var s2 = o.toString();
			if(s2 != "[object Object]") return s2;
		}
		var k = null;
		var str = "{\n";
		s += "\t";
		var hasp = o.hasOwnProperty != null;
		for( var k in o ) { ;
		if(hasp && !o.hasOwnProperty(k)) {
			continue;
		}
		if(k == "prototype" || k == "__class__" || k == "__super__" || k == "__interfaces__" || k == "__properties__") {
			continue;
		}
		if(str.length != 2) str += ", \n";
		str += s + k + " : " + js.Boot.__string_rec(o[k],s);
		}
		s = s.substring(1);
		str += "\n" + s + "}";
		return str;
	case "function":
		return "<function>";
	case "string":
		return o;
	default:
		return String(o);
	}
}
js.Browser = function() { }
js.Browser.__name__ = true;
String.__name__ = true;
Array.__name__ = true;
js.Browser.document = typeof window != "undefined" ? window.document : null;
Vote.main();
})();
