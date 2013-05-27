import js.Browser;
import js.html.Element;
import js.html.InputElement;
import js.html.Event;

class PollForm {

    static function alternativeTmpl(i: Int, inputPlaceholder: String, removeTitle: String): Element {
        var div = Browser.document.createDivElement();
        div.className = 'alternative';
        var inner = Browser.document.createDivElement();
        inner.style.display = 'inline-block';
        inner.style.width = '95%';
        var input = Browser.document.createInputElement();
        input.type = 'text';
        input.name = 'alternatives[' + i + ']';
        input.className = 'required';
        input.placeholder = inputPlaceholder;
        var span = Browser.document.createSpanElement();
        span.className = 'button remove-alternative';
        span.title = removeTitle;
        span.textContent = '×';
        inner.appendChild(input);
        div.appendChild(inner);
        div.appendChild(span);
        return div;
    }

    @:expose public static function main(inputPlaceHolder: String, removeTitle: String) {
        var form = Browser.document.querySelector('form');
        if (form != null) {
            var alternatives = Browser.document.querySelector('.alternatives');
            if (alternatives != null) {

                var renumber = function () {
                    var inputs = alternatives.querySelectorAll('input');
                    for (i in 0...inputs.length) {
                        var input: InputElement = cast inputs.item(i);
                        input.name = 'alternatives[' + i + ']';
                    }
                };

                var removeAlternative = function (e: Event) {
                    var target: Element = cast e.target;
                    if (target.className == 'button remove-alternative') {
                        var a = Dom.closest(target, function (b) { return b.className == 'alternative'; });
                        Dom.remove(a);
                        renumber();
                    }
                };
                untyped {
                    form.addEventListener('click', removeAlternative);
                }

                var addBtn = Browser.document.querySelector('.add-alternative');
                if (addBtn != null) {
                    var addAlternative = function (e: Event) {
                        var alternative = alternativeTmpl(alternatives.querySelectorAll('.alternative').length, inputPlaceHolder, removeTitle);
                        alternatives.appendChild(alternative);
                        var input: InputElement = cast alternative.querySelector('input');
                        if (input != null) {
                            input.focus();
                        }
                    };
                    untyped {
                        addBtn.addEventListener('click', addAlternative);
                    }
                }
            }
        }
    }
}