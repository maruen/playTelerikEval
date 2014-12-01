name := """playTelerikEval"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  javaEbean,
  "org.webjars" % 	"jquery" 				% 	"2.1.1",
  "org.webjars" % 	"bootstrap" 			% 	"3.1.1",
  "org.webjars" % 	"font-awesome" 			% 	"4.1.0",
  "mysql" 		% 	"mysql-connector-java" 	% 	"5.1.18"
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava)
