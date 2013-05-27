import js.html.Element;

class Dom {

    static function loop(e: Element, traverse: Element -> Element, p: Element -> Bool): Element {
        var node = traverse(e);
        if (node == null) return null;
        else if (p(node)) return node;
        else return loop(node, traverse, p);
    }

    public static function closest(e: Element, p: Element -> Bool): Element {
        return loop(e, function (a) { return cast a.parentNode; }, p);
    }

    public static function prev(e: Element, p: Element -> Bool): Element {
        return loop(e, function (a) { return cast a.previousSibling; }, p);
    }

    public static function remove(e: Element) {
        e.parentNode.removeChild(e);
    }
}