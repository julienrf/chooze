define(function () {
  $(document).on('click', '.close-notification', function () {
    $(this).closest('.notification').remove();
  });
});