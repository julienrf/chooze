package javascripts

object JavaScripts {

  val chooze = """(function () {
var x45 = function(x46,x47) {
var x48 = x46;
var x50 = x48.parentNode;
var x59 = undefined;
var x51 = x50;
if (x51 !== null) {
var x49 = x47;
var x52 = x49(x51);
var x57
if (x52) {
var x53 = x51;
x57=x53
} else {
var x55 = x45(x51,x49);
x57=x55
}
x59 = x57;
} else {
x59 = null;
}
return x59
};
var x0 = document.querySelector("form");
if (x0 !== null) {
var x1 = x0;
var x2 = x1.querySelectorAll("input[type=submit], button:not([type=button])");
var x3 = function (ns) { var r = []; for (var i = 0, l = ns.length ; i < l ; i++) r.push(ns.item(i)) ; return r };
var x4 = x3(x2);
x1.addEventListener('submit', function (x5) {
x4.forEach(function (x6) {
x6.disabled = true;
});
}, false);
}
var x13 = undefined;
var x14 = document.querySelector("form.change-locale");
if (x14 !== null) {
var x15 = x14;
x15.addEventListener('change', function (x16) {
var x17 = x15.submit();
}, false);
}
var x21 = undefined;
var x22 = function(x23) {
document.addEventListener('click', function (x24) {
var x25 = x23(x24);
}, false);
};
var x29 = function(x30) {
var x31 = function(x32) {
var x33 = x32.target;
var x34 = x33.classList;
var x35 = x34.contains("button");
var x38
if (x35) {
var x36 = x30(x32);
x38=x36
} else {
x38=undefined
}
};
var x40 = x22(x31);
};
var x61 = function(x62) {
var x63 = x62.classList;
var x64 = x63.contains("notification");
return x64
};
var x68 = function(x69) {
var x70 = x69.parentNode;
var x74 = undefined;
var x71 = x70;
if (x71 !== null) {
var x72 = x71.removeChild(x69);
x74 = x72;
} else {
x74 = undefined;
}
};
var x42 = function(x43) {
var x44 = x43.target;
var x66 = x45(x44,x61);
if (x66 !== null) {
var x67 = x66;
var x76 = x68(x67);
}
var x78 = undefined;
};
var x80 = x29(x42);
}
)()
;"""

  val vote = """(function () {
var x82 = document.querySelector("form");
if (x82 !== null) {
var x83 = x82;
var x100 = function(x101,x102) {
var x103 = x101;
var x105 = x103.previousSibling;
var x114 = undefined;
var x106 = x105;
if (x106 !== null) {
var x104 = x102;
var x107 = x104(x106);
var x112
if (x107) {
var x108 = x106;
x112=x108
} else {
var x110 = x100(x106,x104);
x112=x110
}
x114 = x112;
} else {
x114 = null;
}
return x114
};
var x84 = x83.querySelectorAll(".alternative > input[type=range]");
var x3 = function (ns) { var r = []; for (var i = 0, l = ns.length ; i < l ; i++) r.push(ns.item(i)) ; return r };
var x85 = x3(x84);
var x86 = function(x87) {
x83.addEventListener('change', function (x88) {
var x89 = x87(x88);
}, false);
};
var x116 = function(x117) {
var x118 = x117.className;
var x119 = x118=="name";
return x119
};
var x93 = function(x94) {
var x96 = x94.target;
var x98=x85.filter(function(x95){
var x97 = x95==x96;
return x97
});
x98.forEach(function (x99) {
var x121 = x100(x99,x116);
if (x121 !== null) {
var x122 = x121;
var x124 = x99.value;
var x123 = x122.style;
var x125 = parseInt(x124, 10);
var x126 = x125 * 8;
var x127 = 100 + x126;
x123.fontWeight = x127;
}
var x130 = undefined;
});
};
var x134 = x86(x93);
}
var x136 = undefined;
}
)()
;"""

  val pollForm = """function pollForm(x138, x139) {
var x140 = document.querySelector("form");
if (x140 !== null) {
var x141 = x140;
var x142 = document.querySelector(".alternatives");
if (x142 !== null) {
var x143 = x142;
var x144 = document.querySelector(".add-alternative");
if (x144 !== null) {
var x145 = x144;
var x45 = function(x46,x47) {
var x48 = x46;
var x50 = x48.parentNode;
var x59 = undefined;
var x51 = x50;
if (x51 !== null) {
var x49 = x47;
var x52 = x49(x51);
var x57
if (x52) {
var x53 = x51;
x57=x53
} else {
var x55 = x45(x51,x49);
x57=x55
}
x59 = x57;
} else {
x59 = null;
}
return x59
};
var x146 = function(x147) {
x141.addEventListener('click', function (x148) {
var x149 = x147(x148);
}, false);
};
var x153 = function(x154) {
var x155 = function(x156) {
var x157 = x156.target;
var x158 = x157.className;
var x159 = x158=="button remove-alternative";
var x162
if (x159) {
var x160 = x154(x156);
x162=x160
} else {
x162=undefined
}
};
var x164 = x146(x155);
};
var x169 = function(x170) {
var x171 = x170.className;
var x172 = x171=="alternative";
return x172
};
var x68 = function(x69) {
var x70 = x69.parentNode;
var x74 = undefined;
var x71 = x70;
if (x71 !== null) {
var x72 = x71.removeChild(x69);
x74 = x72;
} else {
x74 = undefined;
}
};
var x3 = function (ns) { var r = []; for (var i = 0, l = ns.length ; i < l ; i++) r.push(ns.item(i)) ; return r };
var x166 = function(x167) {
var x168 = x167.target;
var x174 = x45(x168,x169);
if (x174 !== null) {
var x175 = x174;
var x176 = x68(x175);
var x177 = x143.querySelectorAll("input");
var x178 = x3(x177);
x178.forEach(function (x179, x180) {
var x181 = "alternatives["+x180;
var x182 = x181+"]";
x179.name = x182;
});
}
var x187 = undefined;
};
var x189 = x153(x166);
var x196 = [];
var x203 = x196.length == 0;
x145.addEventListener('click', function (x190) {
var x191 = x143.querySelectorAll(".alternative");
var x192 = x3(x191);
var x193 = x192.length;
var x194 = "alternatives["+x193;
var x195 = x194+"]";
var x197 = document.createElementNS('http://www.w3.org/1999/xhtml', 'input');
x197.setAttribute('name', x195);
x197.setAttribute('placeholder', x138);
x197.setAttribute('class', "required");
x197.setAttribute('type', "text");
x197.setAttribute('value', "");
var x199 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x199.setAttribute('style', "display: inline-block; width: 95%;");
x199.appendChild(x197);
var x200 = document.createTextNode("×");
var x202 = document.createElementNS('http://www.w3.org/1999/xhtml', 'span');
x202.setAttribute('class', "button remove-alternative");
x202.setAttribute('title', x139);
x202.appendChild(x200);
var x211
if (x203) {
var x204 = document.createElementNS('http://www.w3.org/1999/xhtml', 'span');
x211=x204
} else {
var x206 = x196.join(", ");
var x207 = document.createTextNode(x206);
var x209 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x209.setAttribute('class', "error");
x209.appendChild(x207);
x211=x209
}
var x213 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x213.setAttribute('class', "alternative");
x213.appendChild(x199);
x213.appendChild(x202);
x213.appendChild(x211);
var x214 = x213;
var x215 = x143.appendChild(x214);
var x216 = x214.querySelector("input");
if (x216 !== null) {
var x217 = x216;
var x218 = x217.focus();
}
var x220 = undefined;
}, false);
}
var x224 = undefined;
}
var x226 = undefined;
}
var x228 = undefined;
}
"""

}
    
