import sbt.Keys._
import sbt._
import sbtassembly.AssemblyPlugin.autoImport._

object Build extends Build {

  private lazy val librarySettings = Seq(
    name := "spark-examples",
    scalaVersion := "2.11.12",
    organization := "pl.fermich.spark",

    libraryDependencies ++= Dependencies.dependencies,

    ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-target:jvm-1.8"),

    fork in Test := true,
    parallelExecution in Test := false,
    javaOptions in Test += "-Xmx4G",

    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) =>
        (xs map {_.toLowerCase}) match {
          case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) => MergeStrategy.discard
          case "services" :: ss => MergeStrategy.filterDistinctLines
          case _ => MergeStrategy.discard
        }
      case _ => MergeStrategy.first
    }

  )

  lazy val `spark-examples-settings` = (project in file("."))
    .settings(librarySettings)
}
