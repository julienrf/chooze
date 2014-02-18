define(function () {
  var forms = document.querySelectorAll('form');
  for (var i = 0, l = forms.length ; i < l ; i++) {
    var form = forms.item(i);
    var buttons = form.querySelectorAll('input[type=submit], button:not([type=button])');
    form.addEventListener('submit', (function (buttons) {
      return function () {
        for (var j = 0, m = buttons.length ; j < m ; j++) {
          buttons.item(j).disabled = true;
        }
      }
    })(buttons));
  }
});