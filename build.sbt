name := "skicka-lu"

version := "1.0-SNAPSHOT"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.mongodb" %% "casbah" % "2.6.3",
//  "org.reactivemongo" %% "reactivemongo" % "0.9" exclude("org.scala-stm", "scala-stm_2.10.0"),
  "com.novus" %% "salat" % "1.9.5-SNAPSHOT",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "com.typesafe.akka" %% "akka-actor" % "2.1.1"
)     


play.Project.playScalaSettings
