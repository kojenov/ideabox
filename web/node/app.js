var express      = require('express');
var cookieParser = require('cookie-parser');
var crypto       = require('crypto');
var app          = express();

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

// greet the user properly
function greeting(user, res) {
  
  if (user == null) {
    // user provided invalid cookie :(
    res.status(403);
    content = 'Unauthorized';
  }
  
  else {
    content = 'Hello ' + user['name'] + '!'
    
    // let's figure out the role
    if (user['role'] === 'admin') {
      content += ' You are an administrator!'
    }
  }
  res.render('user', {'content': content});
}

app.listen(3000, function () {
  console.log('[in]secure cookie listening on port 3000!');
});

// guest user object
var guestUser = {
  name: 'Guest',
  role: 'guest'
}

// parse the user cookie insecurely (simply trust whatever the user says)
function getUser(req, res) {
  if ('user' in req.cookies) {
    user = JSON.parse(Buffer(req.cookies['user'], 'base64').toString());
  } else {
    user = guestUser;
    res.cookie('user', Buffer(JSON.stringify(user)).toString('base64'), { path: '/insecure'});
  }
  return user;
}

// parse the user cookie securely
function getUserSecure(req, res) {
  // HMAC key
  var key = 'my $up3r $3cr37 k3y'
  
  if ('user' in req.cookies) {
    // separate payload and HMAC
    cookie = req.cookies['user'].split('.');

    // compute the payload's HMAC and compare with the provided HMAC
    if (cookie[1] != crypto.createHmac('sha256', key).update(cookie[0]).digest('base64')) {
      // HMAC doesn't match, we can't accept this
      user = null;
    } else {
      // HMAC matches, we can trust the data
      user = JSON.parse(Buffer(cookie[0], 'base64').toString());
    }
    
  } else {
    // user's first visit, give them a guest cookie
    user = guestUser;
    // base64-encode the payload
    user64 = Buffer(JSON.stringify(user)).toString('base64');
    // compute the HMAC
    hmac = crypto.createHmac('sha256', key).update(user64);
    // combine the payload and HMAC
    res.cookie('user', user64 + '.' + hmac.digest('base64'), { path: '/secure'});
  }
  return user;
}
