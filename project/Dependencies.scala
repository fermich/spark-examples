import sbt._

object Dependencies {

  lazy val dependencies = Seq(
    sparkCore % Compile,
    sparkMl % Compile,

    scalaTest % Test
  )

  val sparkCore = "org.apache.spark" %% "spark-sql" % Versions.sparkVersion
  val sparkMl = "org.apache.spark" %% "spark-mllib" % Versions.sparkVersion

  val scalaTest = "org.scalatest" %% "scalatest" % "2.2.6"
}

object Versions {
  val sparkVersion = "2.3.2"
}
