
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import com.betaocean.LogOverHttp.LogOverHttp


object SparkPi {
  val logger = new LogOverHttp("SparkPi|master")

  // This routine runs on Spark Executors, so define own logger with Spark stage/partition info
  def sample(p: Int) = {
    val taskContext = org.apache.spark.TaskContext.get
    val logger2 = new LogOverHttp(s"SparkPi|S:${taskContext.stageId} P:${taskContext.partitionId}")

    val x = Math.random()
    val y = Math.random()

    if (p % 200 == 0) logger2.debug(s"Iteration count ${p}")
    if (p % 1000 == 0) logger2.info(s"Iteration count ${p}")

    if (x*x + y*y < 1) 1 else 0
  }

  def main(args: Array[String]) = {
    
    val NUM_SAMPLES = args(0).toInt 
    logger.info(s"Using ${NUM_SAMPLES} samples")

    var conf = new SparkConf().setAppName("spark-pi")
    val sc = new SparkContext(conf)

    val count = sc.parallelize(1 to NUM_SAMPLES, 4).map{i => sample(i)}.reduce(_ + _)

    val piEstimate = 4.0 * count / NUM_SAMPLES
    
    logger.info(s"Pi is roughly ${piEstimate}")
    
    if (scala.math.abs(scala.math.Pi - piEstimate )> 0.000000001)
      logger.error("Sorry, not excact enough!")
  }
}
