package pl.fermich.spark

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Seconds, StreamingContext}

trait SparkTestContext {
  private val master = "local[2]"
  private val appName = "SparkLocal"

  def withSparkContext(test: SparkSession => Unit): Unit = {
    val spark = SparkSession.builder
      .master(master)
      .appName(appName)
      .getOrCreate()
    try
      test(spark)
    finally
      spark.stop()
  }

  def withSparkStreamingContext(test: StreamingContext => Unit): Unit = {
    val conf = new SparkConf().setMaster(master).setAppName(appName)
    val ssc = new StreamingContext(conf, Seconds(1))
    try
      test(ssc)
    finally
      ssc.stop()
  }
}
