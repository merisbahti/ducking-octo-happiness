name := "skicka-lu"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.mongodb" %% "casbah" % "2.5.0",
  "com.novus" %% "salat" % "1.9.4"
)     

play.Project.playScalaSettings
