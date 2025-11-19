import akka.actor.typed.{ActorRef, ActorSystem}
import akka.actor.typed.scaladsl.AskPattern._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import BookingActor._
import JsonFormats._
import scala.concurrent.Future
import scala.concurrent.duration._
import akka.http.scaladsl.model.StatusCodes

class Routes(bookingActor: ActorRef[BookingActor.Command])(implicit system: ActorSystem[_]) {

  
  private implicit val timeout: Timeout = Timeout.create(system.settings.config.getDuration("akka.http.server.request-timeout"))

  val bookingRoutes: Route =
    pathPrefix("api") {
      concat(
        path("cabins") {
          get {
            val cabinsFuture: Future[CabinList] = bookingActor.ask(ref => ListCabins(ref))
            complete(cabinsFuture)
          }
        },
        path("book") {
          post {
            entity(as[BookingRequest]) { request =>
              val responseFuture: Future[BookingResponse] = 
                bookingActor.ask(ref => BookCabin(request.cabinId, request.userId, ref))
              complete(responseFuture)
            }
          }
        },
        // NOVA ROTA DE RESET
        path("reset") {
          post {
            bookingActor ! ResetSystem // Manda mensagem de "fogo e esquece"
            complete(StatusCodes.OK)
          }
        }
      )
    }
}