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
    var typ  = printf('%5s: ', req.body.type);
    var host = `${req.body.host.split(".")[0]}`;
    var pid  = printf('[% 6d ]', req.body.pid);
    var tid  = printf('(%10s )', req.body.tid)
    var message = req.body.message;

    if (req.body.type == "ERROR") {
        typ = typ.red
        message = message.red
    }
    var prefix = host + pid + tid + ": "
    if (req.body.tid == "master")
    	console.log(typ + prefix.yellow + message)
   	else
   		console.log(typ + prefix.green + message)

    res.send("OK");
});


// routes will go here

// start the server
app.listen(port);
console.log('Server started! At http://localhost:' + port);