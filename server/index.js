var express = require('express');
var colors = require('colors');
var printf = require('printf');

var app = express();
var port = process.env.PORT || 9999;

var bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


app.post('/echo', function(req, res) {
    // console.log(req.body)
    var typ  = printf('%-5s: ', req.body.type)
    var host = `${req.body.host.split(".")[0]}`
    var pid  = printf('[% 6d]', req.body.pid)
    // var tid  = printf('(%-20s)', req.body.tid.slice(0,20))
    var tid = printf('(%s)', req.body.tid)
    var time = (new Date(req.body.time)).toISOString()
    var message = req.body.message;

    if (req.body.type == "ERROR") {
        typ = typ.red
        message = message.red
    }
    var prefix = host + pid + tid + ": "
  	prefix = (req.body.tid.endsWith("master")) ? prefix.yellow : prefix.green
   	
    console.log(time + " " + typ + prefix + message)

    res.send("OK");
});


// routes will go here

// start the server
app.listen(port);
console.log('Server started! At http://localhost:' + port);