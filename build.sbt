lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.3"

val twitterLibVersion = "18.6.0"

lazy val root = (project in file("."))
  .configs(Test)
  .settings(
    inThisBuild(List(
      organization := "com.rakuten",
      scalaVersion := "2.11.8",
      version := "1.0.0"
    )),
    name := "scrooge",
    libraryDependencies += scalaTest % Test
  )

parallelExecution in Test := false

libraryDependencies ++= Seq(
  "log4j" % "log4j" % "1.2.17",
  "org.influxdb" % "influxdb-java" % "2.9",
  "com.twitter" %% "scrooge-core" % "4.13.0",
  "com.twitter" %% "finagle-thrift" % twitterLibVersion exclude("com.twitter", "libthrift"),
  "com.twitter" %% "twitter-server" % twitterLibVersion,
  "com.twitter" %% "twitter-server-slf4j-log4j12" % twitterLibVersion,
  "joda-time" % "joda-time" % "2.9.3"
)

parallelExecution := false

test in assembly := {}

unmanagedResourceDirectories in Compile += { baseDirectory.value / "src/main/thrift" }

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
