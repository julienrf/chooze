define(function () {
  $(document).on('click', '.notification .button', function () {
    $(this).closest('.notification').remove();
  });
});