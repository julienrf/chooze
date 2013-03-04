package javascripts

trait Chooze extends AntiFlood with Locale with Notification {
  def chooze() = {
    setupAntiFlood()
    handleLocaleChange()
    handleNotifications()
  }
}
