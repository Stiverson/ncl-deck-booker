import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import scala.io.StdIn
import scala.concurrent.ExecutionContext

object Main {
  def main(args: Array[String]): Unit = {
   
    implicit val system = ActorSystem(Behaviors.empty, "CruiseBookingSystem")
   
    implicit val executionContext: ExecutionContext = system.executionContext

    
    val bookingActor = system.systemActorOf(BookingActor(), "BookingActor")

    
    val routes = new Routes(bookingActor)

    
    val bindingFuture = Http().newServerAt("localhost", 8081).bind(routes.bookingRoutes)

    println(s"🚀 Servidor online em http://localhost:8081/api/cabins\nPressione ENTER para parar...")
    StdIn.readLine() 
    
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}