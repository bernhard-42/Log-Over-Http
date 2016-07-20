organization := "com.betaocean"

name := "LogOverHttp"

version := "1.0.0"

scalaVersion := "2.10.6"

libraryDependencies ++= Seq(
	"log4j" % "log4j" % "1.2.17",
	"org.apache.httpcomponents" % "httpclient" % "4.5.2"
)
