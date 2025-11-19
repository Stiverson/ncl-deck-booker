import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object BookingActor {

  sealed trait Command
  case class ListCabins(replyTo: ActorRef[CabinList]) extends Command
  case class BookCabin(cabinId: String, userId: String, replyTo: ActorRef[BookingResponse]) extends Command
  case object ResetSystem extends Command 

  case class CabinList(cabins: Map[String, String])
  case class BookingResponse(success: Boolean, message: String)


  val initialState = Map(
    "A-101" -> "AVAILABLE", "A-102" -> "AVAILABLE", "A-103" -> "BOOKED", "A-104" -> "AVAILABLE",
    "B-201" -> "AVAILABLE", "B-202" -> "BOOKED", "B-203" -> "AVAILABLE", "B-204" -> "AVAILABLE",
    "C-301" -> "BOOKED", "C-302" -> "AVAILABLE", "C-303" -> "AVAILABLE", "C-304" -> "BOOKED"
  )

  def apply(): Behavior[Command] = registry(initialState)

  private def registry(cabins: Map[String, String]): Behavior[Command] =
    Behaviors.receiveMessage {

      case ListCabins(replyTo) =>
        replyTo ! CabinList(cabins)
        Behaviors.same

      case BookCabin(id, user, replyTo) =>
        cabins.get(id) match {
          case Some("AVAILABLE") =>
            println(s"✅ Cabine $id reservada para $user")
            replyTo ! BookingResponse(true, "Reservation confirmed!")
            registry(cabins + (id -> "BOOKED"))

          case Some("BOOKED") =>
            println(s"❌ Conflito! Cabine $id já estava ocupada.")
            replyTo ! BookingResponse(false, "Cabin already booked.")
            Behaviors.same

          case None =>
            replyTo ! BookingResponse(false, "Cabin not found.")
            Behaviors.same

          case Some(status) =>
            replyTo ! BookingResponse(false, s"Unknown status: $status")
            Behaviors.same
        }

      
      case ResetSystem =>
        println("🔄 REALOAD SYSTEM!")
        registry(initialState)
    }
}