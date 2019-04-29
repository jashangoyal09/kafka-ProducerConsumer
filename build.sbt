name := "Producer"

version := "0.1"

scalaVersion := "2.12.8"
libraryDependencies ++= Seq("org.apache.kafka" %% "kafka" % "2.1.0",
  "io.spray" %%  "spray-json" % "1.3.5", 
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "net.manub" %% "scalatest-embedded-kafka" % "0.15.1" % "test",
  "com.typesafe" % "config" % "1.3.3",
  //"org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % "test",
  "org.mockito" % "mockito-core" % "2.7.22"
)

//"com.google.code.gson" %% "gson" % "2.8.5"