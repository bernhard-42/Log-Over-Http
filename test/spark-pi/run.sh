#!/bin/bash

EXECUTOR_DEBUG=beebox02:9999:debug
DRIVER_DEBUG=beebox02:9999:debug

# spark-submit --master local[4] \
spark-submit --master yarn --deploy-mode client \
			 --conf="spark.driver.extraJavaOptions=-Dhttpdebug=$DRIVER_DEBUG" \
			 --conf="spark.executor.extraJavaOptions=-Dhttpdebug=$EXECUTOR_DEBUG" \
             --class SparkPi target/scala-2.10/SparkPi-assembly-1.0.jar $@