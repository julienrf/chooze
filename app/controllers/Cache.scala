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
import play.api.cache.{Cache => PlayCache}
import java.util.UUID

trait Cache {

  def Cached(identifier: String)(result: => PlainResult)(implicit request: RequestHeader, lang: Lang): Result = {
    if (current.mode == Mode.Dev) { // Chuis deg.
      result
    } else {
      val resourceEtag = getOrCreateEtag(identifier)
      request.headers.get(IF_NONE_MATCH) match {
        case Some(etag) if etag == resourceEtag => {
          Logger.debug("'%s' Not Modified".format(request.uri))
          NotModified
        }
        case _ => {
          PlayCache.getOrElse(resourceEtag, 3600 * 24) {
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
    for (resourceEtag <- PlayCache.getAs[String](etagKey(identifier))) {
      PlayCache.set(etagKey(identifier), resourceEtag, 1) // Sounds weird? That’s because there’s no PlayCache.remove(key)
      // PlayCache.set(resourceEtag, null, 1)
    }
  }

  private def getOrCreateEtag(identifier: String)(implicit lang: Lang): String = {
    PlayCache.getOrElse(etagKey(identifier))(Codecs.sha1(UUID.randomUUID().toString))
  }

  private def etagKey(identifier: String)(implicit lang: Lang) = "cache-key:" + identifier + "-" + lang
}