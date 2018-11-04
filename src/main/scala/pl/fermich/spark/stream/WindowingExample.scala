package pl.fermich.spark.stream

import org.apache.spark.streaming.{Milliseconds, Seconds}
import org.apache.spark.streaming.dstream.DStream

class WindowingExample {

  def window(pairs: DStream[Int]) = {
    val windowedWordCounts = pairs.reduceByWindow((a:Int,b:Int) => {
      println(s"a=$a b=$b")
      (a + b)
    }, Seconds(6), Seconds(2))

    windowedWordCounts.foreachRDD(rdd => {
      rdd.foreach(e => println(s"Sum=$e"))
    })
  }

}
