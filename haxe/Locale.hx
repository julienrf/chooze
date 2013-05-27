import js.Browser;
import js.html.FormElement;
import js.html.Event;

class Locale {
    public static function main () {
        var form: FormElement = cast Browser.document.querySelector('form.change-locale');
        var submit = function (e: Event) {
            form.submit();
        }
        untyped {
            form.addEventListener('change', submit);
        }
    }
}