import pickle
import base64
from flask import Flask
from flask import request
from flask import render_template
from flask import make_response

app = Flask(__name__)

@app.route('/')
def index():
  res = make_response(render_template('index.html', text=""))
  sessionCookie = request.cookies.get('hai_session')
  if sessionCookie:
    session = pickle.loads(base64.b64decode(sessionCookie))
  else:
    session = {}
    session['user'] = "anonymous"
    session['access'] = "none"
    res.set_cookie('hai_session',base64.b64encode(pickle.dumps(session)))
  return res
