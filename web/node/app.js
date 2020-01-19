var express = require('express');
var cookieParser = require('cookie-parser');
var crypto = require('crypto');
var app = express();

app.use(cookieParser());
app.set('view engine', 'pug');

app.get('/', function (req, res) {
  res.render('index');
});

app.get('/insecure', function (req, res) {
  greeting(getUser(req, res), res);
});

app.get('/secure', function (req, res) {
   greeting(getUserSecure(req, res), res);
});

function greeting(user, res) {
  if (user == null) {
    res.status(403);
    content = 'Unauthorized';
  }
  else {
    if (user['role'] === 'admin') {
      content = user['name'] + '... welcome, administrator!'
    } else {
      content = user['name'] + '... sorry, you are not an admin'
    }
  }
  res.render('user', {'content': content});
}

app.listen(3000, function () {
  console.log('[in]secure cookie listening on port 3000!');
});

var anonUser = {
  name: 'Visitor',
  role: 'anonymous'
}

function getUser(req, res) {
  if ('user' in req.cookies) {
    user = JSON.parse(Buffer(req.cookies['user'], 'base64').toString());
  } else {
    user = anonUser;
    res.cookie('user', Buffer(JSON.stringify(user)).toString('base64'), { path: '/insecure'});
  }
  return user;
}

function getUserSecure(req, res) {
  var key = 'my $up3r $3cr37 k3y'
  if ('user' in req.cookies) {
    cookie = req.cookies['user'].split('.');

    if (cookie[1] != crypto.createHmac('sha256', key).update(cookie[0]).digest('base64')) {
      user = null;
    } else {
      user = JSON.parse(Buffer(cookie[0], 'base64').toString());
    }
  } else {
    user = anonUser;
    user64 = Buffer(JSON.stringify(user)).toString('base64');
    hmac = crypto.createHmac('sha256', key).update(user64);
    res.cookie('user', user64 + '.' + hmac.digest('base64'), { path: '/secure'});
  }
  return user;
}
