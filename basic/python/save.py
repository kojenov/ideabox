#!/usr/bin/python3
import pickle
import random

mydata = {'name'   : 'The answer',
          'id'     : 42
#         ,'bytes'   : bytearray(b'\xDE\xAD\xBE\xEF')
         }

with open('object.ser', 'wb') as data_file:
  pickle.dump(mydata, data_file)
  print("saved object to object.ser:")
  print(mydata)
