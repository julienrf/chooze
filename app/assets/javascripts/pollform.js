(function () { "use strict";
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
var PollForm = function() { }
PollForm.alternativeTmpl = function(i,inputPlaceholder,removeTitle) {
	var div = js.Browser.document.createElement("div");
	div.className = "alternative";
	var inner = js.Browser.document.createElement("div");
	inner.style.display = "inline-block";
	inner.style.width = "95%";
	var input = js.Browser.document.createElement("input");
	input.type = "text";
	input.name = "alternatives[" + i + "]";
	input.className = "required";
	input.placeholder = inputPlaceholder;
	var span = js.Browser.document.createElement("span");
	span.className = "button remove-alternative";
	span.title = removeTitle;
	span.textContent = "×";
	inner.appendChild(input);
	div.appendChild(inner);
	div.appendChild(span);
	return div;
}
PollForm.main = function(inputPlaceHolder,removeTitle) {
	var form = js.Browser.document.querySelector("form");
	if(form != null) {
		var alternatives = js.Browser.document.querySelector(".alternatives");
		if(alternatives != null) {
			var renumber = function() {
				var inputs = alternatives.querySelectorAll("input");
				var _g1 = 0, _g = inputs.length;
				while(_g1 < _g) {
					var i = _g1++;
					var input = inputs.item(i);
					input.name = "alternatives[" + i + "]";
				}
			};
			var removeAlternative = function(e) {
				var target = e.target;
				if(target.className == "button remove-alternative") {
					var a = Dom.closest(target,function(b) {
						return b.className == "alternative";
					});
					Dom.remove(a);
					renumber();
				}
			};
			form.addEventListener("click",removeAlternative);
			var addBtn = js.Browser.document.querySelector(".add-alternative");
			if(addBtn != null) {
				var addAlternative = function(e) {
					var alternative = PollForm.alternativeTmpl(alternatives.querySelectorAll(".alternative").length,inputPlaceHolder,removeTitle);
					alternatives.appendChild(alternative);
					var input = alternative.querySelector("input");
					if(input != null) input.focus();
				};
				addBtn.addEventListener("click",addAlternative);
			}
		}
	}
}
$hxExpose(PollForm.main, "PollForm.main");
var js = {}
js.Browser = function() { }
js.Browser.document = typeof window != "undefined" ? window.document : null;
function $hxExpose(src, path) {
	var o = typeof window != "undefined" ? window : exports;
	var parts = path.split(".");
	for(var ii = 0; ii < parts.length-1; ++ii) {
		var p = parts[ii];
		if(typeof o[p] == "undefined") o[p] = {};
		o = o[p];
	}
	o[parts[parts.length-1]] = src;
}
})();
