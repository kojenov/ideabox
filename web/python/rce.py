import pickle
import base64
from flask import Flask
from flask import request
from flask import render_template
from flask import make_response

app = Flask(__name__)

@app.route('/')
def index():
  # prepare the response
  res = make_response(render_template('index.html', text=""))
  
  # did the user send a session cookie?
  sessionCookie = request.cookies.get('hai_session')
  
  if sessionCookie:
    # deserialize the cookie, YOLO!
    session = pickle.loads(base64.b64decode(sessionCookie))
  
  else:
    # generate and return session cookie to a first time visitor
    session = {}
    session['user'] = "anonymous"
    session['access'] = "none"
    res.set_cookie('hai_session', base64.b64encode(pickle.dumps(session)))

  return res
