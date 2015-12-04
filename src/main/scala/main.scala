package example

import unfiltered.request._
import unfiltered.response._
import unfiltered.netty._

object Main {
  def main(args: Array[String]): Unit = {
    val p = this.getClass.getResource("/www/")
    unfiltered.netty.Server
      .http(8080, "127.0.0.1")
      .resources(p, cacheSeconds = 3600)
      .handler(Angular)
      .run
  }
}

import java.net.URI
import java.io.OutputStream

@io.netty.channel.ChannelHandler.Sharable
object Angular extends cycle.Plan with cycle.SynchronousExecution with ServerErrorResponse {
  def intent: cycle.Plan.Intent = {
    case GET(Path("/")) =>
      responseStreamer("ui/index.html")

    case GET(Path(Seg("ui" :: path))) =>
      responseStreamer("ui/"+path.mkString("/"))

    // WARNING: make sure that this handler is the absolutle last
    // in the chain, otherwise it will gobble up every request.
    case _ =>
      responseStreamer("ui/index.html")
  }

  private def responseStreamer(path: String) = new ResponseStreamer {
    def stream(os: OutputStream){
      val is = getClass.getClassLoader.getResourceAsStream(path)
      try {
        val buf = new Array[Byte](1024)
        Iterator.continually(is.read(buf))
          .takeWhile(_ != -1)
          .foreach(os.write(buf, 0, _))
      } finally {
        is.close
      }
    }
  }
}
