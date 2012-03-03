$('form').on('submit', function (e) {
  $(this).find('input[type=submit], button[type!=button]').each(function () {
    this.disabled = true
  })
})