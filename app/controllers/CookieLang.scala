package controllers

import play.api.mvc.{Action, Controller, Cookie, RequestHeader}
import play.api.i18n.Lang
import play.api.data.Form
import play.api.data._
import play.api.data.Forms._
import play.api.Logger

trait CookieLang extends Controller {

  val localeForm = Form("locale" -> nonEmptyText)

  def changeLocale = Action { implicit request =>
    val referrer = request.headers.get(REFERER).getOrElse(HOME_URL)
    localeForm.bindFromRequest.fold(
      errors => {
        Logger.logger.debug("The locale can not be change to : " + errors.get)
        BadRequest(referrer)
      },
      locale => {
        Logger.logger.debug("Change user lang to : " + locale)
        Redirect(referrer).withCookies(Cookie(LANG, locale))
      })
  }

  override implicit def lang(implicit request: RequestHeader) = {
    request.cookies.get(LANG) match {
      case None => super.lang(request)
      case Some(cookie) => Lang(cookie.value)
    }
  }

  private val LANG = "lang"
  protected val HOME_URL = "/"
}