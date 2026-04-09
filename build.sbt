val ProjectName      = "ribosome"
val OrganisationName = "emc"
val ProjectVersion   = "0.3.3"

val ScalaVersion     = "3.8.2"

lazy val root = (project in file("."))
  .settings(
    scalaVersion := ScalaVersion,
    organization := OrganisationName,
    version      := ProjectVersion,
    name         := ProjectName,
    libraryDependencies ++= Seq(
      "org.scalatest"     %% "scalatest" % "3.2.19"  % "test"
    )
  )

ThisBuild / scalacOptions ++= Seq(
  "-encoding", "utf8",
  "-feature",
  "-language:implicitConversions",
  "-language:existentials",
  "-language:strictEquality",
  "-unchecked",
  "-Werror",
  "-deprecation"
)
