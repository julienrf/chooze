package commons

import play.api.mvc.{Action, Controller, Cookie, RequestHeader}
import play.api.i18n.Lang
import play.api.data.Form
import play.api.data._
import play.api.data.Forms._
import play.api.Logger

trait CookieLang extends Controller {
  val localeForm = Form("locale" -> text)

  def changeLocale = Action { implicit request =>
    val referer = request.headers.get(REFERER).get
    localeForm.bindFromRequest.fold(
      errors => {
        Logger.logger.debug("The locale can not be change to : " + errors.get)
        BadRequest(referer)
      },
      locale => {
        Logger.logger.debug("Change user lang to : " + locale)
        Redirect(referer).withCookies(Cookie("LANG", locale))
      })
  }

  override implicit def lang(implicit request: RequestHeader) = {
    request.cookies.get("LANG") match {
      case None => super.lang(request)
      case Some(cookie) => Lang(cookie.value)
    }
  }
}