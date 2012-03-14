package controllers

import play.api.mvc.{Result, PlainResult, RequestHeader, Results}
import Results.NotModified
import play.api.http.HeaderNames.{IF_NONE_MATCH, ETAG}
import play.api.libs.Codecs
import play.api.Play.current
import play.api.Mode
import java.util.Date
import play.api.Logger
import play.api.i18n.Lang
import java.util.UUID

trait Cache {

  def Cached(identifier: String)(result: => PlainResult)(implicit request: RequestHeader, lang: Lang): Result = {
    if (current.mode == Mode.Dev) { // Chuis deg.
      result
    } else {
      val resourceEtag = etag(identifier)
      request.headers.get(IF_NONE_MATCH) match {
        case Some(etag) if etag == resourceEtag => {
          Logger.debug("'%s' Not Modified".format(request.uri))
          NotModified
        }
        case _ => {
          play.api.cache.Cache.getOrElse (resourceEtag) {
            Logger.debug("Compute '%s'".format(request.uri))
            result.withHeaders(ETAG -> resourceEtag)
          }
        }
      }
    }
  }

  def Cached(result: => PlainResult)(implicit request: RequestHeader, lang: Lang): Result = {
    Cached(request.uri)(result)
  }

  def clearEtag(identifier: String)(implicit lang: Lang) = {
    play.api.cache.Cache.set(etagKey(identifier), null, 1)
  }

  private def etag(identifier: String)(implicit lang: Lang): String = {
    play.api.cache.Cache.getOrElse(etagKey(identifier))(Codecs.sha1(new Date().toString))
  }

  private def etagKey(identifier: String)(implicit lang: Lang) = "cache-key:" + identifier + "-" + lang
}