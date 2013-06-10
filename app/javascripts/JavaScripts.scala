package javascripts

object JavaScripts {

  val chooze = """(function () {
var x59 = function(x60,x61) {
var x62 = x60;
var x64 = x62.parentNode;
var x75 = null;
if (x64 !== null) {
var x65 = x64;
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
var x73 = null;
if (x71 !== null) {
var x72 = x71;
x73 = x72;
}
x75 = x73;
}
return x75
};
var x0 = document.getElementsByTagName("form");
for (var x98 = 0, x99 = x0.length ; x98 < x99 ; x98++) {
var x1 = x0.item(x98);
var x2 = x1.querySelectorAll("input[type=submit], button:not([type=button])");
var x3 = function(x4) {
x1.addEventListener('submit', function (x5) {
var x6 = x4(x5);
}, false);
};
var x10 = function(x11) {
for (var x100 = 0, x101 = x2.length ; x100 < x101 ; x100++) {
var x12 = x2.item(x100);
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
var x77 = function(x78) {
var x79 = x78.classList;
var x80 = x79.contains("notification");
return x80
};
var x84 = function(x85) {
var x86 = x85.parentNode;
var x90 = undefined;
if (x86 !== null) {
var x87 = x86;
var x88 = x87.removeChild(x85);
x90 = x88;
} else {
x90 = undefined;
}
};
var x56 = function(x57) {
var x58 = x57.target;
var x82 = x59(x58,x77);
if (x82 !== null) {
var x83 = x82;
var x92 = x84(x83);
}
var x94 = undefined;
};
var x96 = x43(x56);
}
)()
"""

  val vote = """(function () {
var x102 = document.querySelector("form");
if (x102 !== null) {
var x103 = x102;
var x119 = function(x120,x121) {
var x122 = x120;
var x124 = x122.previousSibling;
var x135 = null;
if (x124 !== null) {
var x125 = x124;
var x123 = x121;
var x126 = x123(x125);
var x131
if (x126) {
var x127 = x125;
x131=x127
} else {
var x129 = x119(x125,x123);
x131=x129
}
var x133 = null;
if (x131 !== null) {
var x132 = x131;
x133 = x132;
}
x135 = x133;
}
return x135
};
var x104 = x103.querySelectorAll(".alternative > input[type=range]");
var x105 = function(x106) {
x103.addEventListener('change', function (x107) {
var x108 = x106(x107);
}, false);
};
var x137 = function(x138) {
var x139 = x138.className;
var x140 = x139=="name";
return x140
};
var x112 = function(x113) {
var x115 = x113.target;
var x117 = [];
for (var x159 = 0, x160 = x104.length ; x159 < x160 ; x159++) {
var x114 = x104.item(x159);
var x116 = x114==x115;
if (x116) x117.push(x114);
}
x117.forEach(function (x118) {
var x142 = x119(x118,x137);
if (x142 !== null) {
var x143 = x142;
var x145 = x118.value;
var x144 = x143.style;
var x146 = parseInt(x145, 10);
var x147 = x146 * 8;
var x148 = 100 + x147;
x144.fontWeight = x148;
}
var x151 = undefined;
});
};
var x155 = x105(x112);
}
var x157 = undefined;
}
)()
"""

  val pollForm = """function pollForm(x161, x162) {
var x163 = document.querySelector("form");
if (x163 !== null) {
var x164 = x163;
var x165 = document.querySelector(".alternatives");
if (x165 !== null) {
var x166 = x165;
var x167 = document.querySelector(".add-alternative");
if (x167 !== null) {
var x168 = x167;
var x59 = function(x60,x61) {
var x62 = x60;
var x64 = x62.parentNode;
var x75 = null;
if (x64 !== null) {
var x65 = x64;
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
var x73 = null;
if (x71 !== null) {
var x72 = x71;
x73 = x72;
}
x75 = x73;
}
return x75
};
var x169 = function(x170) {
x164.addEventListener('click', function (x171) {
var x172 = x170(x171);
}, false);
};
var x176 = function(x177) {
var x178 = function(x179) {
var x180 = x179.target;
var x181 = x180.className;
var x182 = x181=="button remove-alternative";
var x185
if (x182) {
var x183 = x177(x179);
x185=x183
} else {
x185=undefined
}
};
var x187 = x169(x178);
};
var x192 = function(x193) {
var x194 = x193.className;
var x195 = x194=="alternative";
return x195
};
var x84 = function(x85) {
var x86 = x85.parentNode;
var x90 = undefined;
if (x86 !== null) {
var x87 = x86;
var x88 = x87.removeChild(x85);
x90 = x88;
} else {
x90 = undefined;
}
};
var x189 = function(x190) {
var x191 = x190.target;
var x197 = x59(x191,x192);
if (x197 !== null) {
var x198 = x197;
var x199 = x84(x198);
var x200 = x166.getElementsByTagName("input");
for (var x202 = 0, x259 = x200.length ; x202 < x259 ; x202++) {
var x201 = x200.item(x202);
var x203 = "alternatives["+x202;
var x204 = x203+"]";
x201.name = x204;
}
}
var x209 = undefined;
};
var x211 = x176(x189);
var x212 = function(x213) {
x168.addEventListener('click', function (x214) {
var x215 = x213(x214);
}, false);
};
var x225 = [];
var x232 = x225.length == 0;
var x235 = x225.join(", ");
var x219 = function(x220) {
var x221 = x166.getElementsByClassName("alternative");
var x222 = x221.length;
var x223 = "alternatives["+x222;
var x224 = x223+"]";
var x226 = document.createElementNS('http://www.w3.org/1999/xhtml', 'input');
x226.setAttribute('name', x224);
x226.setAttribute('placeholder', x161);
x226.setAttribute('class', "required");
x226.setAttribute('type', "text");
x226.setAttribute('value', "");
var x228 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x228.setAttribute('style', "display: inline-block; width: 95%;");
x228.appendChild(x226);
var x229 = document.createTextNode("×");
var x231 = document.createElementNS('http://www.w3.org/1999/xhtml', 'span');
x231.setAttribute('class', "button remove-alternative");
x231.setAttribute('title', x162);
x231.appendChild(x229);
var x240
if (x232) {
var x233 = document.createElementNS('http://www.w3.org/1999/xhtml', 'span');
x240=x233
} else {
var x236 = document.createTextNode(x235);
var x238 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x238.setAttribute('class', "error");
x238.appendChild(x236);
x240=x238
}
var x242 = document.createElementNS('http://www.w3.org/1999/xhtml', 'div');
x242.setAttribute('class', "alternative");
x242.appendChild(x228);
x242.appendChild(x231);
x242.appendChild(x240);
var x243 = x242;
var x244 = x166.appendChild(x243);
var x245 = x243.querySelector("input");
if (x245 !== null) {
var x246 = x245;
var x247 = x246.focus();
}
var x249 = undefined;
};
var x251 = x212(x219);
}
var x253 = undefined;
}
var x255 = undefined;
}
var x257 = undefined;
}
"""

}
