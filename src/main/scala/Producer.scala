import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import spray.json.{DefaultJsonProtocol, JsArray, JsBoolean, JsString, JsValue, RootJsonFormat, deserializationError, _}

import scala.io.StdIn

object Producer extends App {

  import MyJsonProtocol._
  val json = GameData("Session10", "gameStage10", "levelId10", "stageId10", true).toJson

  import MyJsonProtocol._

  val json2 = GameData("Session1230", "gameSt324age10", "level234Id10", "sta234geId10", true).toJson
  val props = new Properties()
  //val color = json.convertTo[GameData]
  println("this is json " + json)
  val producer = new KafkaProducer[String, String](props)
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val TOPIC = "test"
  // val TOPIC = "topic"
  val exit = StdIn.readLine("Enter 1 to send and enter 0 to exit :\n").toInt


  //can change to other library I am using spray.json here.
  object MyJsonProtocol extends DefaultJsonProtocol {

    implicit object ColorJsonFormat extends RootJsonFormat[GameData] {
      def write(c: GameData) =
        JsArray(JsString(c.sessionId), JsString(c.gameId), JsString(c.levelId), JsString(c.stageId), JsBoolean(c.stageCompleted))

      def read(value: JsValue) = value match {
        case JsArray(Vector(JsString(sessionId), JsString(gameId), JsString(levelId), JsString(stageId), JsBoolean(stageCompleted))) =>
          new GameData(sessionId, gameId, levelId, stageId, stageCompleted)
        case _ => deserializationError("GameData expected")
      }
    }

  }

  exit match {
    case 1 =>
      val record = new ProducerRecord(TOPIC, "key", json.toString)
      producer.send(record)
      producer.close()
    case 0 =>
      println("Data is not produced")
      System.exit(0)
  }
}

