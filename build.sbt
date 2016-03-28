organization := "com.bbva.mike"
name := "nacar-test"

scalaVersion := "2.11.6"


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-ws" % "2.4.6",
  "com.typesafe.akka" %% "akka-actor" % "2.3.14",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "2.0.3",
  "org.apache.kafka" %% "kafka" % "0.9.0.1" excludeAll(ExclusionRule(organization = "com.sun.jdmk",name="jmxtools"),
    ExclusionRule(organization = "com.sun.jmx",name="jmxri"),
    ExclusionRule(organization = "javax.jms",name="jms"))
)

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith(".idea")          => MergeStrategy.discard
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*")      => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.copy(`classifier` = Some("assembly"))
}

addArtifact(artifact in (Compile, assembly), assembly)

publishMavenStyle := true


publishArtifact in Test := false

pomIncludeRepository := { _ => false }
