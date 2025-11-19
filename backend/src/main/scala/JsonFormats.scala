import spray.json.{DefaultJsonProtocol, RootJsonFormat}
import BookingActor._

object JsonFormats extends DefaultJsonProtocol {
 
  implicit val cabinListFormat: RootJsonFormat[CabinList] = jsonFormat1(CabinList)
  implicit val bookingResponseFormat: RootJsonFormat[BookingResponse] = jsonFormat2(BookingResponse)
  
  case class BookingRequest(cabinId: String, userId: String)
  implicit val bookingRequestFormat: RootJsonFormat[BookingRequest] = jsonFormat2(BookingRequest)
}