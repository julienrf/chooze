define(function () {

  var loop = function (e, traverse, p) {
    var node = traverse(e);
    if (node === null) return null;
    else if (p(node) === true) return node;
    else return loop(node, traverse, p)
  };

  return {
    closest: function (e, p) {
      return loop(e, function (_) { return _.parentNode }, p)
    },
    prev: function (e, p) {
      return loop(e, function (_) { return _.previousSibling }, p)
    },
    remove: function (e) {
      e.parentNode.removeChild(e);
    }
  }
});