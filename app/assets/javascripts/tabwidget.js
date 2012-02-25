(function () {
  $('.tab-widget').each(function () {
    var tabs = $(this).children('.tabs').children();
    var containers = $(this).children('.containers').children();
    
    tabs.change(function () {
      containers.hide();
      containers.eq($(this).index()).show();
    })
    
    tabs.first().find('input[type=radio]').click();
  })
})();