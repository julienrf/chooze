import js.Browser;
import js.html.Event;
import js.html.Element;
import js.html.InputElement;

class AntiFlood {
    public static function main() {
        var fs = Browser.document.querySelectorAll('form');
        for (i in 0...fs.length) {
            var f: Element = cast fs.item(i);
            var disableButtons = function (e: Event) {
                var is = f.querySelectorAll('input[type=submit], button:not([type=button])');
                for (j in 0...is.length) {
                    var i: InputElement = cast is.item(j);
                    i.disabled = true;
                }
            }
            untyped {
                f.addEventListener("submit", disableButtons);
            }
        }
    }
}