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

trait Cache {

  def Cached(lastModified: Date)(result: => PlainResult)(implicit request: RequestHeader, lang: Lang): Result = {
    if (current.mode == Mode.Dev) { // Chuis deg.
      result
    } else {
      val resourceEtag = etagFor(request.uri + "-" + lang + "-" + lastModified.getTime)
      request.headers.get(IF_NONE_MATCH) match {
        case Some(etag) if etag == resourceEtag => {
          Logger.debug("'%s' Not Modified")
          NotModified
        }
        case _ => {
          play.api.cache.Cache.getOrElse {
            Logger.debug("Get '%s' from cache".format(request.uri))
            resourceEtag
          } {
            Logger.debug("Compute '%s'".format(request.uri))
            result.withHeaders(ETAG -> resourceEtag)
          }
        }
      }
    }
  }

  def Cached(result: => PlainResult)(implicit request: RequestHeader, lang: Lang): Result = {
    Cached(new Date(0))(result)
  }

  private val etags = collection.mutable.Map.empty[String, String]

  private def etagFor(identifier: String): String = {
    etags.get(identifier).getOrElse {
      val etag = Codecs.sha1(identifier)
      etags += identifier -> etag
      etag
    }
  }
}