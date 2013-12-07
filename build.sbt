name := "skicka-lu"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.reactivemongo" %% "reactivemongo" % "0.9" exclude("org.scala-stm", "scala-stm_2.10.0")
)     


play.Project.playScalaSettings
