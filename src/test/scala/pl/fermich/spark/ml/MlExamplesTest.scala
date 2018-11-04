package pl.fermich.spark.ml

import org.apache.spark.ml.linalg.Vectors
import org.scalatest.{FlatSpec, Matchers}
import pl.fermich.spark.SparkTestContext


class MlExamplesTest extends FlatSpec with Matchers with SparkTestContext {

  it should "train with logistic regression" in {
    withSparkContext { spark =>
      // Prepare training data from a list of (label, features) tuples.
      val training = spark.createDataFrame(Seq(
        (1.0, Vectors.dense(0.0, 1.1, 0.1)),
        (0.0, Vectors.dense(2.0, 1.0, -1.0)),
        (0.0, Vectors.dense(2.0, 1.3, 1.0)),
        (1.0, Vectors.dense(0.0, 1.2, -0.5))
      )).toDF("label", "features")

      // Prepare test data.
      val testing = spark.createDataFrame(Seq(
        (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
        (0.0, Vectors.dense(3.0, 2.0, -0.1)),
        (1.0, Vectors.dense(0.0, 2.2, -1.5))
      )).toDF("label", "features")


      val regression = new RegressionExample(spark)
      regression.trainLogisticRegression(training, testing)
    }
  }

  it should "train in pipeline" in {
    withSparkContext { spark =>
      // Prepare training documents from a list of (id, text, label) tuples.
      val training = spark.createDataFrame(Seq(
        (0L, "a b c d e spark", 1.0),
        (1L, "b d", 0.0),
        (2L, "spark f g h", 1.0),
        (3L, "hadoop mapreduce", 0.0)
      )).toDF("id", "text", "label")

      // Prepare test documents, which are unlabeled (id, text) tuples.
      val testing = spark.createDataFrame(Seq(
        (4L, "spark i j k"),
        (5L, "l m n"),
        (6L, "spark hadoop spark"),
        (7L, "apache hadoop")
      )).toDF("id", "text")

      val pipe1 = new PipelineExample(spark)
      pipe1.runPipeline(training, testing)
    }
  }

  it should "tune hyperparameters" in {
    withSparkContext { spark =>
      // Prepare training data from a list of (id, text, label) tuples.
      val training = spark.createDataFrame(Seq(
        (0L, "a b c d e spark", 1.0),
        (1L, "b d", 0.0),
        (2L, "spark f g h", 1.0),
        (3L, "hadoop mapreduce", 0.0),
        (4L, "b spark who", 1.0),
        (5L, "g d a y", 0.0),
        (6L, "spark fly", 1.0),
        (7L, "was mapreduce", 0.0),
        (8L, "e spark program", 1.0),
        (9L, "a e c l", 0.0),
        (10L, "spark compile", 1.0),
        (11L, "hadoop software", 0.0)
      )).toDF("id", "text", "label")

      // Prepare test documents, which are unlabeled (id, text) tuples.
      val testing = spark.createDataFrame(Seq(
        (4L, "spark i j k"),
        (5L, "l m n"),
        (6L, "mapreduce spark"),
        (7L, "apache hadoop")
      )).toDF("id", "text")

      val tuning = new HyperparameterTunning(spark)
      tuning.tune(training, testing)
    }
  }
}
