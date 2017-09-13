import time
import socket
import requests

class HttpLogger(object):

    def __init__(self, taskId, url):
        self.taskId = taskId
        self.url = url
        self.level = "ERROR"

    def setLevel(self, level):
        self.level = level

    def send(self, typ, message):
        json = {"time": 1000 * int(time.time()),
                "type": typ,
                "host": socket.gethostname(),
                "pid": os.getpid(),
                "tid": str(self.taskId),
                "message": message}
        requests.post(self.url, json=json)

    def debug(self, message):
        if self.level in ["DEBUG"]:
            self.send("DEBUG", message)

    def info(self, message):
        if self.level in ["INFO", "DEBUG"]:
            self.send("INFO", message)

    def error(self, message):
        if self.level in ["INFO", "DEBUG", "ERROR"]:
            self.send("ERROR", message)

