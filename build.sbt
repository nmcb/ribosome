val ProjectName      = "ribosome"
val OrganisationName = "emc"
val ProjectVersion   = "0.3.2"

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
