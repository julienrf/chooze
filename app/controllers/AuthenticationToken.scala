package controllers

import play.api.mvc._
import java.util.UUID
import play.api.data.Form
import play.api.templates.{Html, HtmlFormat}

case class AuthToken(value: String)
sealed trait Authenticated
case object Authenticated extends Authenticated

trait AuthenticationToken {
  this: Controller =>

  /**
   * Get (or generate) an authentication token for the current session
   * @param result Continuation computing the result using the token
   */
  def authenticationToken(result: AuthToken => PlainResult)(implicit request: RequestHeader): PlainResult = {
    val token = session.get(AuthenticationToken.AUTH_TOKEN).getOrElse(UUID.randomUUID().toString)
    result(AuthToken(token)).withSession(AuthenticationToken.AUTH_TOKEN -> token)
  }

  /**
   * Check the authenticity of a given token against the token found in the session
   * @param maybeResult The result to return
   * @return Some(result) if there is a token in the session and it has the same value as the token param, otherwise None
   */
  def checkAuthenticity(implicit request: Request[AnyContent]): Option[Authenticated] = {
    for {
      token <- findAuthToken(request.body)
      authToken <- session.get(AuthenticationToken.AUTH_TOKEN)
      if token == authToken
    } yield {
      Authenticated
    }
  }
  
  /**
   * Try to find the Authentication Token in the request body
   */
  private def findAuthToken(content: AnyContent): Option[String] = content match {
    case AnyContentAsFormUrlEncoded(data) => {
      for {
        entry <- data.get(AuthenticationToken.AUTH_TOKEN)
        token <- entry.headOption
      } yield token
    }
    case AnyContentAsMultipartFormData(data) => {
      for {
        entry <- data.dataParts.get(AuthenticationToken.AUTH_TOKEN)
        token <- entry.headOption
      } yield token
    }
    case _ => None
  }
}

object AuthenticationToken {

  private val AUTH_TOKEN = "auth_token"

  def hiddenField(implicit token: AuthToken): Html = {
    Html("""<input type="hidden" name="%s" value="%s" />""".format(HtmlFormat.escape(AUTH_TOKEN), HtmlFormat.escape(token.value)))
  }
}