#!/usr/bin/python3
import pickle

with open('object.ser', 'rb') as data_file:
  mydata = pickle.load(data_file)
  print("read object from object.ser:")
  print(mydata)
