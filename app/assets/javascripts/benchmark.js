;(function () {

  /**
   * Adds 1000 alternatives and then removes them.
   */
  var benchmark = function () {
    var start = (new Date).getTime();
    var btn = document.querySelector('button.add-alternative');
    for (var i = 0 ; i < 1000 ; i++) {
      btn.click();
    }
    var btns = document.querySelectorAll('.button.remove-alternative');
    for (var j = 0, n = btns.length ; j < n ; j++) {
      btns.item(j).click();
    }
    var diff = (new Date).getTime() - start;
    alert(diff + ' ms');
  };

  var btn = document.createElement('button');
  btn.textContent = 'Run benchmark';
  btn.addEventListener('click', benchmark);
  document.body.appendChild(btn);

})();