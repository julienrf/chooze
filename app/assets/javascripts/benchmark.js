;(function () {

  /**
   * Stolen from Google: https://developers.google.com/speed/articles/javascript-dom
   * Remove an element and provide a function that inserts it into its original position
   * @param element {Element} The element to be temporarily removed
   * @return {Function} A function that inserts the element into its original position
   **/
  function removeToInsertLater(element) {
    var parentNode = element.parentNode;
    var nextSibling = element.nextSibling;
    parentNode.removeChild(element);
    return function() {
      if (nextSibling) {
        parentNode.insertBefore(element, nextSibling);
      } else {
        parentNode.appendChild(element);
      }
    };
  }

  /**
   * Adds 1000 alternatives and then removes them.
   */
  var benchmark = function () {

    var form = document.querySelector('form');
    var restore = removeToInsertLater(form);

    var start = (new Date).getTime();
    var btn = form.querySelector('button.add-alternative');
    for (var i = 0 ; i < 2000 ; i++) {
      btn.click();
    }
    /*var btns = document.querySelectorAll('.button.remove-alternative');
    for (var j = 0, n = btns.length ; j < n ; j++) {
      btns.item(j).click();
    }*/
    var diff = (new Date).getTime() - start;

    restore();

    alert(diff + ' ms');
  };

  var btn = document.createElement('button');
  btn.textContent = 'Run benchmark';
  btn.addEventListener('click', benchmark);
  document.body.appendChild(btn);

})();