define(function () {
  var form = document.querySelector('form.change-locale');
  if (form !== null) {
    form.addEventListener('change', function () {
      form.submit();
    });
  }
});
