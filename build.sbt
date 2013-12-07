name := "skicka-lu"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.reactivemongo" %% "reactivemongo" % "0.9" exclude("org.scala-stm", "scala-stm_2.10.0",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test")
)     


play.Project.playScalaSettings
