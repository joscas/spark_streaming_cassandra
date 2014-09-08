import AssemblyKeys._

assemblySettings

jarName in assembly := "StreamingDemo.jar"

name := "StreamingDemo"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.0.2" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.0.2" % "provided",
  "com.datastax.cassandra" % "cassandra-driver-core" % "2.0.5"
)