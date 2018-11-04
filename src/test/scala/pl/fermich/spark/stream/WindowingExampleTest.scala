package pl.fermich.spark.stream

import org.apache.spark.rdd.RDD
import org.scalatest.{FlatSpec, Matchers}
import pl.fermich.spark.SparkTestContext

import scala.collection.mutable


class WindowingExampleTest extends FlatSpec with Matchers with SparkTestContext {

  it should "sum numbers in a window" in {
    withSparkStreamingContext{ ssc =>
      val q: mutable.Queue[RDD[Int]] = new mutable.Queue[RDD[Int]]()

      val dstream = ssc.queueStream(q)
      q.enqueue(ssc.sparkContext.parallelize(Seq(1)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(2)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(3)))

      q.enqueue(ssc.sparkContext.parallelize(Seq(4)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(5)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(6)))

      q.enqueue(ssc.sparkContext.parallelize(Seq(7)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(8)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(9)))

      q.enqueue(ssc.sparkContext.parallelize(Seq(10)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(11)))
      q.enqueue(ssc.sparkContext.parallelize(Seq(12)))

      val windowing = new WindowingExample()
      windowing.window(dstream)

      ssc.start()

      ssc.awaitTermination()
    }
  }
}
