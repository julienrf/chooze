package controllers

import play.api.mvc.{Result, PlainResult, RequestHeader, Results}
import Results.NotModified
import play.api.http.HeaderNames.{IF_NONE_MATCH, ETAG}
import play.api.libs.Codecs
import play.api.cache.Cache
import play.api.Play.current
import play.api.Mode

import java.util.Date

trait BrowserCache {

  private val etags = collection.mutable.Map.empty[String, String]

  private def etagFor(identifier: String): String = {
    etags.get(identifier).getOrElse {
      val etag = Codecs.sha1(identifier)
      etags += identifier -> etag
      etag
    }
  }

  def browserCached(lastModified: Date)(result: => PlainResult)(implicit request: RequestHeader): Result = {
    if (current.mode == Mode.Dev) {
      result
    } else {
      val resourceEtag = etagFor(request.uri + " - " + lastModified.getTime)
      request.headers.get(IF_NONE_MATCH) match {
        case Some(etag) if etag == resourceEtag => NotModified
        case _ => Cache.getOrElse(resourceEtag) {
          result.withHeaders(ETAG -> resourceEtag)
        }
      }
    }
  }

  def browserCached(result: => PlainResult)(implicit request: RequestHeader): Result = {
    browserCached(new Date(0))(result)
  }
}