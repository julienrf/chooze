define(function () {
  var form = document.querySelector('form');
  if (form !== null) {
    var buttons = form.querySelectorAll('input[type=submit], button:not([type=button])');
    form.addEventListener('submit', function () {
      for (var i = 0, l = buttons.length ; i < l ; i++) {
        buttons.item(i).disabled = true;
      }
    });
  }
});