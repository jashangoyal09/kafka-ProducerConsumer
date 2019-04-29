import scala.beans.BeanProperty

class GameDataBean() {
  @BeanProperty var sessionId: String = _
  @BeanProperty var gameId: String = _
  @BeanProperty var levelId: String = _
  @BeanProperty var stageId: String = _
  @BeanProperty var stageCompleted: Boolean = _

  def toCase: GameData = {
    GameData(sessionId, gameId, levelId, stageId, stageCompleted)
  }
}

case class GameData(
  sessionId: String,
  gameId: String,
  levelId: String,
  stageId: String,
  stageCompleted: Boolean) {

  def toBean: GameDataBean = {
    val gameData = new GameDataBean()
    gameData.sessionId = sessionId
    gameData.gameId = gameId
    gameData.levelId = levelId
    gameData.stageId = stageId
    gameData.stageCompleted = stageCompleted
    gameData
  }
}
