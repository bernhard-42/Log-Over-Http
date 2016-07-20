## Compile LogOverHttp

	sbt publish-local


## Use LogOverHttp

Add

	"com.betaocean" % "logoverhttp_2.10" % "1.0.0"

to `build.sbt`

In your code 

	import com.betaocean.LogOverHttp.LogOverHttp

then initilaize it twice:

1) For master 

	object MyCoolSparkProject {

		val logoverhttp = new LogOverHttp("master")
		...
	}

2) for all code that runs on executors

	val taskContext = org.apache.spark.TaskContext.get
	val logoverhttp2 = new LogOverHttp(s"P:${taskContext.partitionId} S:${taskContext.stageId}")


## Start log server

	cd server
	npm install
	node index.js

## Output

Logging from master:

	 INFO: beebox02[  4199](    master): Some intelligent message
Logging from executors

	 INFO: beebox06[ 23387](   P:1;S:0): Some intelligent distributed message
	 INFO: beebox03[  4388](   P:0;S:0): Some intelligent distributed message

`P:` is the partition id and `S:` the stage id of the executore task 