lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.3"

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
  "org.apache.thrift" % "libthrift" % "0.11.0",
  "joda-time" % "joda-time" % "2.9.3"
)

unmanagedSourceDirectories in Compile += baseDirectory.value / "src" / "main" / "thrift-java"

parallelExecution := false

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
