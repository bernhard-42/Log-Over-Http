name := "SparkPi"

version := "1.0"

scalaVersion := "2.10.6"

resolvers += "Hortonworks Repository" at "http://repo.hortonworks.com/content/repositories/releases/"
resolvers += "Hortonworks Jetty Maven Repository" at "http://repo.hortonworks.com/content/repositories/jetty-hadoop/"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-sql_2.10"  % "1.6.1.2.4.2.0-258" % "provided",
  "com.betaocean" % "logoverhttp_2.10" % "1.0.0"
)
