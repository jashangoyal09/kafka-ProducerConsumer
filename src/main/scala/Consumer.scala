import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.KafkaConsumer
import spray.json._

import scala.collection.JavaConverters._

object Consumer extends App {
  val TOPIC = "test"
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")
  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(util.Collections.singletonList(TOPIC))
  val TopicOfProducer = ""
  while (true) {
    val records = consumer.poll(100)
    for (record <- records.asScala) {
      println("This is recorde " + record)
      val gameData = record.value()
      //val data =   json.JsonParser(gameData).convertTo[GameData](JsonReader[GameData])
      //val data1 = JSON.parseFull(gameData)

      object MyJsonProtocol extends DefaultJsonProtocol {

        implicit object ColorJsonFormat extends RootJsonFormat[GameData] {
          def write(c: GameData) =
            JsArray(JsString(c.sessionId), JsString(c.gameId), JsString(c.levelId), JsString(c.stageId), JsBoolean(c.stageCompleted))

          def read(value: JsValue) = value match {
            case JsArray(Vector(JsString(sessionId), JsString(gameId), JsString(levelId), JsString(stageId), JsBoolean(stageCompleted))) =>
              GameData(sessionId, gameId, levelId, stageId, stageCompleted)
            case _ => deserializationError("GameData expected")
          }
        }

      }
      import MyJsonProtocol._
      //val json1 = gameData.toJson
      val json = gameData.parseJson

      val data: GameData = json.convertTo[GameData]
      val dataBean: GameDataBean = data.toBean
      println(data.sessionId)


    }
  }
}
