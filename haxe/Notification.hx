import js.Browser;
import js.html.Event;
import js.html.Element;

class Notification {
    public static function main() {
        var handleNotification = function (e: Event) {
            var target: Element = cast e.target;
            if (target.classList.contains('close-notification')) {
                var notification = Dom.closest(target, function (e: Element): Bool {
                    return e.classList.contains('notification');
                });
                if (notification != null) {
                    Dom.remove(notification);
                }
            }
        };
        untyped {
            Browser.document.addEventListener('click', handleNotification);
        }
    }
}