## Compile LogOverHttp

	sbt publish-local


## Use LogOverHttp

Add

	"com.betaocean" % "logoverhttp_2.10" % "1.0.0"

to `build.sbt`

In your code 

	import com.betaocean.LogOverHttp.LogOverHttp

then initialize it twice:

1) For master 

	object MyCoolSparkProject {

		val logoverhttp = new LogOverHttp("master")
		...
	}

2) for all code that runs on executors

	val taskContext = org.apache.spark.TaskContext.get
	val logoverhttp2 = new LogOverHttp(s"P:${taskContext.partitionId} S:${taskContext.stageId}")

Use

	logoverhttp.info("Some intelligent message")
	logoverhttp.error("Some intelligent message")
	logoverhttp2.debug("Some intelligent distributed message")

## Start log server

	cd server
	npm install
	node index.js

## Test

	cd test/spark-pi
	sbt assembly
	./runs.sh 10

leads to

	 INFO: beebox02[ 15048 ](    master ): Using 10 samples 
	DEBUG: beebox06[ 28771 ](   P:0 S:0 ): 0.12937289910647454/0.6641207572647878 ==> 1
	DEBUG: beebox02[ 15273 ](   P:1 S:0 ): 0.46585855084939554/0.37581020830802303 ==> 1
	DEBUG: beebox06[ 28771 ](   P:0 S:0 ): 0.35393680511039394/0.6817052288251934 ==> 1
	DEBUG: beebox02[ 15273 ](   P:1 S:0 ): 0.6273643986213433/0.9188328551086075 ==> 0
	DEBUG: beebox02[ 15273 ](   P:1 S:0 ): 0.17691957058921337/0.6585821776588496 ==> 1
	DEBUG: beebox06[ 28771 ](   P:2 S:0 ): 0.28074914071415324/0.6936149913817252 ==> 1
	DEBUG: beebox06[ 28771 ](   P:2 S:0 ): 0.6269557954479293/0.19717443680263025 ==> 1
	DEBUG: beebox02[ 15273 ](   P:3 S:0 ): 0.6488140536518237/0.3043726251612864 ==> 1
	DEBUG: beebox02[ 15273 ](   P:3 S:0 ): 0.9884035502098925/0.7581686488824158 ==> 0
	DEBUG: beebox02[ 15273 ](   P:3 S:0 ): 0.7547640283436756/0.5854021649234818 ==> 1
	 INFO: beebox02[ 15048 ](    master ): Pi is roughly 3.2

In number in `[]` is the process id (pid) and in `()` the value for `P:` is the partition id and for `S:` the stage id of the executor task 