import js.Browser;
import js.html.Event;
import js.html.InputElement;


class Vote {
    public static function main() {
        var form = Browser.document.querySelector('form');
        if (form != null) {
            var update = function (e: Event) {
                var target: InputElement = cast e.target;
                var inputs = form.querySelectorAll('.alternative > input[type=range]');
                for (i in 0...inputs.length) {
                    if (inputs.item(i) == e.target) {
                        var n = Dom.prev(target, function (a) { return a.className == 'name'; });
                        if (n != null) {
                            n.style.fontWeight = Std.string(100 + Std.parseInt(target.value) * (8));
                        }
                    }
                }
            };
            untyped {
                form.addEventListener('change', update);
            }
        }
    }
}