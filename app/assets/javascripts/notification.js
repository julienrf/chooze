define(['dom'], function (dom) {
  document.addEventListener('click', function (e) {
    if (e.target.classList.contains('button')) {
      var notification = dom.closest(e.target, function (_) {
        return _.classList.contains('notification')
      });
      if (notification !== null) {
        dom.remove(notification);
      }
    }
  });
});