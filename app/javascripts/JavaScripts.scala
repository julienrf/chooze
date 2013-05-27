package javascripts

object JavaScripts {

  val chooze = """(function () {
var x59 = function(x60,x61) {
var x62 = x60;
var x64 = x62.parentNode;
var x73 = undefined;
var x65 = x64;
if (x65 !== null) {
var x63 = x61;
var x66 = x63(x65);
var x71
if (x66) {
var x67 = x65;
x71=x67
} else {
var x69 = x59(x65,x63);
x71=x69
}
x73 = x71;
} else {
x73 = null;
}
return x73
};
var x0 = document.getElementsByTagName("form");
for (var x96 = 0, x97 = x0.length ; x96 < x97 ; x96++) {
var x1 = x0.item(x96);
var x2 = x1.querySelectorAll("input[type=submit], button:not([type=button])");
var x3 = function(x4) {
x1.addEventListener('submit', function (x5) {
var x6 = x4(x5);
}, false);
};
var x10 = function(x11) {
for (var x98 = 0, x99 = x2.length ; x98 < x99 ; x98++) {
var x12 = x2.item(x98);
x12.disabled = true;
}
};
var x17 = x3(x10);
}
var x20 = document.querySelector("form.change-locale");
if (x20 !== null) {
var x21 = x20;
var x22 = function(x23) {
x21.addEventListener('change', function (x24) {
var x25 = x23(x24);
}, false);
};
var x29 = function(x30) {
var x31 = x21.submit();
};
var x33 = x22(x29);
}
var x35 = undefined;
var x36 = function(x37) {
document.addEventListener('click', function (x38) {
var x39 = x37(x38);
}, false);
};
var x43 = function(x44) {
var x45 = function(x46) {
var x47 = x46.target;
var x48 = x47.classList;
var x49 = x48.contains("close-notification");
var x52
if (x49) {
var x50 = x44(x46);
x52=x50
} else {
x52=undefined
}
};
var x54 = x36(x45);
};
var x75 = function(x76) {
var x77 = x76.classList;
var x78 = x77.contains("notification");
return x78
};
var x82 = function(x83) {
var x84 = x83.parentNode;
var x88 = undefined;
var x85 = x84;
if (x85 !== null) {
var x86 = x85.removeChild(x83);
x88 = x86;
} else {
x88 = undefined;
}
};
var x56 = function(x57) {
var x58 = x57.target;
var x80 = x59(x58,x75);
if (x80 !== null) {
var x81 = x80;
var x90 = x82(x81);
}
var x92 = undefined;
};
var x94 = x43(x56);
}
)()
"""

  val vote = """(function () {
var x100 = document.querySelector("form");
if (x100 !== null) {
var x101 = x100;
var x117 = function(x118,x119) {
var x120 = x118;
var x122 = x120.previousSibling;
var x131 = undefined;
var x123 = x122;
if (x123 !== null) {
var x121 = x119;
var x124 = x121(x123);
var x129
if (x124) {
var x125 = x123;
x129=x125
} else {
var x127 = x117(x123,x121);
x129=x127
}
x131 = x129;
} else {
x131 = null;
}
return x131
};
var x102 = x101.querySelectorAll(".alternative > input[type=range]");
var x103 = function(x104) {
x101.addEventListener('change', function (x105) {
var x106 = x104(x105);
}, false);
};
var x133 = function(x134) {
var x135 = x134.className;
var x136 = x135=="name";
return x136
};
var x110 = function(x111) {
var x113 = x111.target;
var x115 = [];
for (var x155 = 0, x156 = x102.length ; x155 < x156 ; x155++) {
var x112 = x102.item(x155);
var x114 = x112==x113;
if (x114) x115.push(x112);
}
x115.forEach(function (x116) {
var x138 = x117(x116,x133);
if (x138 !== null) {
var x139 = x138;
var x141 = x116.value;
var x140 = x139.style;
var x142 = parseInt(x141, 10);
var x143 = x142 * 8;
var x144 = 100 + x143;
x140.fontWeight = x144;
}
var x147 = undefined;
});
};
var x151 = x103(x110);
}
var x153 = undefined;
}
)()
"""

  val pollForm = """function pollForm(x157, x158) {
var x159 = document.querySelector("form");
if (x159 !== null) {
var x160 = x159;
var x161 = document.querySelector(".alternatives");
if (x161 !== null) {
var x162 = x161;
var x163 = document.querySelector(".add-alternative");
if (x163 !== null) {
var x164 = x163;
var x59 = function(x60,x61) {
var x62 = x60;
var x64 = x62.parentNode;
var x73 = undefined;
var x65 = x64;
if (x65 !== null) {
var x63 = x61;
var x66 = x63(x65);
var x71
if (x66) {
var x67 = x65;
x71=x67
} else {
var x69 = x59(x65,x63);
x71=x69
}
x73 = x71;
} else {
x73 = null;
}
return x73
};
var x165 = function(x166) {
x160.addEventListener('click', function (x167) {
var x168 = x166(x167);
}, false);
};
var x172 = function(x173) {
var x174 = function(x175) {
var x176 = x175.target;
var x177 = x176.className;
var x178 = x177=="button remove-alternative";
var x181
if (x178) {
var x179 = x173(x175);
x181=x179
} else {
x181=undefined
}
};
var x183 = x165(x174);
};
var x188 = function(x189) {
var x190 = x189.className;
var x191 = x190=="alternative";
return x191
};
var x82 = function(x83) {
var x84 = x83.parentNode;
var x88 = undefined;
var x85 = x84;
if (x85 !== null) {
var x86 = x85.removeChild(x83);
x88 = x86;
} else {
x88 = undefined;
}
};
var x185 = function(x186) {
var x187 = x186.target;
var x193 = x59(x187,x188);
if (x193 !== null) {
var x194 = x193;
var x195 = x82(x194);
var x196 = x162.getElementsByTagName("input");
for (var x198 = 0, x255 = x196.length ; x198 < x255 ; x198++) {
var x197 = x196.item(x198);
var x199 = "alternatives["+x198;
var x200 = x199+"]";
x197.name = x200;
}
}
var x205 = undefined;
};
var x207 = x172(x185);
var x208 = function(x209) {
x164.addEventListener('click', function (x210) {
var x211 = x209(x210);
}, false);
};
var x221 = [];
var x228 = x221.length == 0;
var x231 = x221.join(", ");
var x215 = function(x216) {
var x217 = x162.getElementsByClassName("alternative");
var x218 = x217.length;
var x219 = "alternatives["+x218;
var x220 = x219+"]";
var x222 = document.createElementNS('http://www.w3.org/1999/xhtml', 'input');
x222.setAttribute('name', x220);
x222.setAttribute('placeholder', x157);
x222.setAttribute('class', "required");
x222.setAttribute('type', "text");
x222.setAttribute('value', "");
var x224 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x224.setAttribute('style', "display: inline-block; width: 95%;");
x224.appendChild(x222);
var x225 = document.createTextNode("×");
var x227 = document.createElementNS('http://www.w3.org/1999/xhtml', 'span');
x227.setAttribute('class', "button remove-alternative");
x227.setAttribute('title', x158);
x227.appendChild(x225);
var x236
if (x228) {
var x229 = document.createElementNS('http://www.w3.org/1999/xhtml', 'span');
x236=x229
} else {
var x232 = document.createTextNode(x231);
var x234 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x234.setAttribute('class', "error");
x234.appendChild(x232);
x236=x234
}
var x238 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x238.setAttribute('class', "alternative");
x238.appendChild(x224);
x238.appendChild(x227);
x238.appendChild(x236);
var x239 = x238;
var x240 = x162.appendChild(x239);
var x241 = x239.querySelector("input");
if (x241 !== null) {
var x242 = x241;
var x243 = x242.focus();
}
var x245 = undefined;
};
var x247 = x208(x215);
}
var x249 = undefined;
}
var x251 = undefined;
}
var x253 = undefined;
}
"""

}
