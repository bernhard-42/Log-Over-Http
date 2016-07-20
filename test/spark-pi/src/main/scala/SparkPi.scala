
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import org.apache.log4j.{Level, Logger}

import com.betaocean.LogOverHttp.LogOverHttp


object SparkPi {
  val logoverhttp = new LogOverHttp("master")

  def sample(p: Int) = {
    val taskContext = org.apache.spark.TaskContext.get
    val logoverhttp2 = new LogOverHttp(s"P:${taskContext.partitionId} S:${taskContext.stageId}")

    val x = Math.random()
    val y = Math.random()
    if (x*x + y*y < 1) {
      logoverhttp2.debug(s"${x}/${y} ==> 1")
      1
    } else {
      logoverhttp2.debug(s"${x}/${y} ==> 0")
      0
    }
  }

  def main(args: Array[String]) = {
    
    Logger.getRootLogger.setLevel(Level.ERROR)

    val NUM_SAMPLES = args(0).toInt 
    logoverhttp.info(s"Using ${NUM_SAMPLES} samples")

    var conf = new SparkConf().setAppName("spark-pi")
    val sc = new SparkContext(conf)

    val count = sc.parallelize(1 to NUM_SAMPLES, 4).map{i => sample(i)}.reduce(_ + _)

    logoverhttp.info("Pi is roughly " + 4.0 * count / NUM_SAMPLES)
  }
}
