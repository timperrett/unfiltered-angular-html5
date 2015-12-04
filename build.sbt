
Revolver.settings

scalaVersion := "2.10.5"

organization := "com.timperrett"

name := "unfiltered-angular"

libraryDependencies ++= Seq(
  "net.databinder" %% "unfiltered-filter"       % "0.8.4",
  "net.databinder" %% "unfiltered-netty-server" % "0.8.4"
)
