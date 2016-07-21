package com.betaocean.LogOverHttp

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.log4j.Logger

class LogOverHttp(taskId: String) {
    val logger = Logger.getLogger(taskId)

    val debugOption = System.getProperty("httpdebug")
    val (webhost, port, debugMode) = 
        if (debugOption == null) {
            logger.info("Logging not enabled")
            ("", 0, "none")
        } else {
            try {
                val parts = debugOption.split(":")
                val (webhost, port, debugMode) = (parts(0), parts(1).toInt, parts(2))
                logger.info(s"Messages for mode ${debugMode} mode log messages will be sent to http://${webhost}:${port}")
                (webhost, port, debugMode)
            } catch {
                case e: Exception => {
                    logger.error(s"Wrong format: ${debugOption}")
                    ("", -2, "none")
                }
            }
        }

    def send(typ: String, message: String) = {
        val post = new HttpPost(s"http://${webhost}:${port}/echo")

        val runtime = java.lang.management.ManagementFactory.getRuntimeMXBean().getName()
        val host = runtime.split("@")(1)
        val pid  = runtime.split("@")(0)
        
        val json = s"""{"time":${System.currentTimeMillis}, "type":"${typ}", "host":"${host}",""" + 
                   s""" "pid":${pid}, "tid":"${taskId}", "message":"${message.replaceAll("\"", "'")}"}"""

        post.setHeader("Content-type", "application/json")
        post.setEntity(new StringEntity(json))
        
        // fire and forget ...
        try {
            val response = (new DefaultHttpClient).execute(post)
        } catch {
            case e: Exception => logger.error("Send message ignored, webhost gone")
        }
    }

    def error(message: String) = {
        if (List("debug", "info", "error").contains(debugMode)) { 
            logger.error(message)
            send("ERROR", message) 
        }
    }

    def info(message: String) = {
        if (List("debug", "info").contains(debugMode)) { 
            logger.info(message)
            send("INFO", message) 
        }
    }

    def debug(message: String) = {
        if (List("debug").contains(debugMode)) { 
            logger.debug(message)
            send("DEBUG", message) 
        }
    }
}

