define(function () {
  document.addEventListener('click', function (e) {
    if (e.target.classList.contains('close-notification')) {
      var notification = (function loop (el) {
        var p = el.parentNode;
        if (p === null) return null; else {
          return p.classList.contains('notification') ? p : loop(p)
        }
      })(e.target);
      if (notification !== null) {
        notification.parentNode.removeChild(notification);
      }
    }
  });
});